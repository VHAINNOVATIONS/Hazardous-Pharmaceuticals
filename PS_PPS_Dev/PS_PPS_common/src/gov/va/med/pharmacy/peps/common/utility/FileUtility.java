/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility;


import java.io.File;


/**
 * Contains various methods for manipulating files and directories.
 */
public class FileUtility {

    
    /**
     * Cannot instantiate.
     */
    private FileUtility() {
        super();
    }
    
    /**
     * Deletes directory and it's contents
     * 
     * @param dir - directory to be deleted
     * @return boolean to indicate success
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();

            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));

                if (!success) {

                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }


}
