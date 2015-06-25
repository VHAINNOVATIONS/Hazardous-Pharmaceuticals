/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.CommonException;


/**
 * Formats text for a word processing field entry.
 */
public class WordProcessingFormatter {

    private static final char SPACE = ' ';
    private static final char NEWLINE = '|';

    private String text;

    /**
     * Construct a formatter.
     * 
     * @param text text to format
     */
    public WordProcessingFormatter(String text) {
        if (text == null) {
            throw new CommonException(CommonException.ILLEGAL_ARGUMENT, "text can not be null");
        }

        this.text = text;
    }

    /**
     * Reformat a text block into the VistA preferred text format.
     * 
     * @param lineLength maximum characters in a line
     * @param textSize maximum characters in the block
     * @param noBrokenWords true to disallow broken word(s)
     * @return formatted text
     */
    public String format(int lineLength, int textSize, boolean noBrokenWords) {
        List<Part> parts = extractParts();
        int blocks = 0;

        // check for broken words, splitting them if necessary
        for (int i = 0; i < parts.size(); i++) {
            Part part = parts.get(i);

            if ((part.getText().length() + 1) > lineLength) { // word + space
                if (noBrokenWords) {
                    throw new CommonException(CommonException.ILLEGAL_ARGUMENT, "lineLength");
                }

                StringBuilder partSplitter = new StringBuilder(part.getText());

                // split whole words (accounting for whitespace)
                for (int j = lineLength
                        - (part.hasLeadingSpace() ? 1 : 0), offset = 0, length = partSplitter.length(); j < length; j +=
                        lineLength, offset++) {
                    if ((length - j) < lineLength) { // send overspill to the beginning of the next part
                        if ((i + 1) < parts.size()) { // next part exists, so prefix
                            Part nextPart = parts.get(i + 1);
                            nextPart.setText(partSplitter.substring(j + offset, partSplitter.length()) + SPACE
                                + parts.get(i + 1).getText());
                            nextPart.setLeadingSpace(false);
                        } else { // next part does not exist
                            parts.add(new Part(partSplitter.substring(j + offset, partSplitter.length()), false));
                        }

                        partSplitter.delete(j + offset, partSplitter.length()); // not necessary, but clarifies logic
                    } else { // insert a newline delimiter in current part
                        partSplitter.insert(j + offset, NEWLINE);
                        blocks++;
                    }
                }

                part.setText(partSplitter.toString());
            }
        }

        StringBuilder formattedText = new StringBuilder();

        for (int i = 0, length = 0; i < parts.size(); i++) {
            if ((i > 0) && ((length + parts.get(i).getText().length()) > lineLength)) { // current line
                formattedText.append(NEWLINE);
                length = 0; // reset length
                blocks++;
            }

            if ((i > 0) && parts.get(i).hasLeadingSpace()) {
                formattedText.append(SPACE);
                length++;
            }

            formattedText.append(parts.get(i).getText());
            length += parts.get(i).getText().length();
        }

        // truncate text past the specified size
        if ((formattedText.length() - blocks) > textSize) {
            formattedText.setLength(textSize + (int) Math.floor(textSize / (double) lineLength));
        }

        return formattedText.toString();
    }

    /**
     * Extracts the word parts.
     * 
     * @return parts
     */
    private List<Part> extractParts() {
        List<Part> parts = new ArrayList<Part>();

        for (String piece : text.split("\\s")) {
            if (piece.length() > 0) {
                parts.add(new Part(piece, true));
            }
        }

        parts.get(0).setLeadingSpace(false);

        return parts;
    }

    /**
     * Represents a whole word.
     */
    private static class Part {

        private String text;
        private boolean leadingSpace;

        /**
         * Create a part.
         * 
         * @param text text
         * @param leadingSpace true if leading space
         */
        public Part(String text, boolean leadingSpace) {
            this.text = text;
            this.leadingSpace = leadingSpace;
        }

        /**
         * Get text.
         * 
         * @return text
         */
        public String getText() {
            return text;
        }

        /**
         * Set text.
         * 
         * @param text text
         */
        public void setText(String text) {
            this.text = text;
        }

        /**
         * If leading space is required.
         * 
         * @return true if leading space
         */
        public boolean hasLeadingSpace() {
            return leadingSpace;
        }

        /**
         * Set leading space.
         * 
         * @param leadingSpace true if leading spacer
         */
        public void setLeadingSpace(boolean leadingSpace) {
            this.leadingSpace = leadingSpace;
        }

        /**
         * To string.
         * 
         * @return string
         * 
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return text;
        }
    }
}
