/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test;


import java.io.File;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.SerializationUtility;
import gov.va.med.pharmacy.peps.common.utility.test.generator.NdcGenerator;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;

import junit.framework.TestCase;


/**
 * Test serialization functionality. This test case does not test all methods on the {@link SerializationUtility} class, but
 * does test the methods in which the bulk of the logic exists. The remaining methods exist as convenience methods to allow
 * for various ways to specify the file in which to serialize or to deserialize from.
 */
public class SerializationUtilityTest extends TestCase {

    private static final NdcVo NDC = new NdcGenerator().getFirst();
    private static final String TEMP_DIRECTORY = System.getProperty("java.io.tmpdir");
    private static final String TEMP_FILENAME = "tempfile";

    /**
     * Test serialization creates a file
     */
    public void testSerialize() {

        String filename = SerializationUtility.serialize(NDC);

        File file = new File(filename);

        assertTrue("File should exist", file.exists());
    }

    /**
     * Verify serializing with a directory and file name works.
     */
    public void testSerializeWithPath() {

        boolean written = SerializationUtility.serialize(TEMP_DIRECTORY, TEMP_FILENAME, NDC, true);

        assertTrue("serialize should return true", written);
    }

    /**
     * Verify that telling the {@link SerializationUtility#serialize(String, String, java.io.Serializable, boolean)} to not
     * overwrite actually doesn't overwrite.
     */
    public void testDeserializeWithoutOverwrite() {

        SerializationUtility.serialize(TEMP_DIRECTORY, TEMP_FILENAME, NDC, true); // be sure a file is present
        boolean written = SerializationUtility.serialize(TEMP_DIRECTORY, TEMP_FILENAME, NDC, false);

        assertFalse("serialize should return false", written);
    }

    /**
     * Deserialize and delete a the file in one operation.
     */
    public void testDeserialize() {

        String filename = SerializationUtility.serialize(NDC);
        NdcVo ndc = SerializationUtility.deserialize(filename);

        assertNotNull("NDC is null!", ndc);

        File file = new File(filename);

        assertFalse("File should no longer exist", file.exists());
    }

    /**
     * Deserialize without deleting the file.
     */
    public void testDeserializeNotDeletFile() {

        String filename = SerializationUtility.serialize(NDC);
        NdcVo ndc = SerializationUtility.deserialize(filename, false);

        assertNotNull("NDC is null", ndc);

        File file = new File(filename);

        assertTrue("File should still exist", file.exists());
    }

    /**
     * The serialized and deserialized objects should be equal.
     */
    public void testSerializeDeserialize() {

        String filename = SerializationUtility.serialize(NDC);
        NdcVo deserialized = SerializationUtility.deserialize(filename);

        assertEquals("The serialized and deserialized objects should be equal ", NDC, deserialized);
    }

    /**
     * The serialized and deserialized objects should be equal.
     */
    public void testSerializeDeserializeWithPath() {

        SerializationUtility.serialize(TEMP_DIRECTORY, TEMP_FILENAME, NDC, true);
        NdcVo deserialized = SerializationUtility.deserialize(TEMP_DIRECTORY, TEMP_FILENAME);

        assertEquals("The serialized and deserialized objects should be equal", NDC, deserialized);
    }

    /**
     * Verify that deserializing all objects from a folder returns the correct number of files
     */
    public void testDeserializeAll() {

        String folderPath = TEMP_DIRECTORY + File.separator + "testing";
        File folder = new File(folderPath);

        // delete any existing files
        if (folder.exists()) {
            File[] files = folder.listFiles();

            for (File file : files) {
                file.delete();
            }
        } else {
            folder.mkdirs();
        }

        final int numFiles = 10;

        // Create 10 new files
        for (int i = 0; i < numFiles; i++) {
            SerializationUtility.serialize(folderPath, TEMP_FILENAME + i, NDC, true);
        }

        List<NdcVo> ndcs = SerializationUtility.deserializeAll(folderPath, true);

        assertEquals("should have " + numFiles + " objects", numFiles, ndcs.size());
    }

    /**
     * Verify that serializing to a String produces a non-null String with a length greater than 0.
     */
    public void testSerializeToString() {

        String serialized = SerializationUtility.serializeToString(NDC);

        assertNotNull("Serialized object should not be null", serialized);
        assertTrue("Serialized String should have a trimed length greater than 0", serialized.trim().length() > 0);
    }

    /**
     * Verify that the serialize/deserialize to/from a String process produces two equal objects.
     */
    public void testDeserializeFromString() {

        String serialized = SerializationUtility.serializeToString(NDC);
        NdcVo deserialized = SerializationUtility.deserializeFromString(serialized);

        assertNotNull("Deserialized object should not be null", deserialized);
        assertEquals("Original and deserialized objects should be equal", NDC, deserialized);
    }
}
