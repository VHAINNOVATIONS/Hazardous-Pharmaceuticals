/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.SerializationException;
import org.apache.commons.lang.SerializationUtils;

import gov.va.med.pharmacy.peps.common.exception.CommonException;


/**
 * Provides utilities for serialization of {@link Serializable}.
 */
public class SerializationUtility {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SerializationUtility.class);
    private static final String DEFAULT_TEMPORARY_FILE_PREFIX = "tmp";
    private static final String PATH = "./tmp/";
    private static final Charset CHARSET = Charset.forName("ISO-8859-1");


    /**
     * Cannot instantiate.
     */
    private SerializationUtility() {
        super();
    }

    /**
     * Deserialize object from file. Delete the file after deserialization is complete. If the given {@link File} cannot be
     * found to deserialize, null is returned.
     * 
     * @param <T> Type of {@link Serializable}
     * @param file {@link File} from which to deserialize object
     * @return ValueObject
     */
    public static <T extends Serializable> T deserialize(File file) {
        return (T) deserialize(file, true);
    }

    /**
     * Deserialize object from file. If the given {@link File} cannot be found to deserialize, null is returned.
     * 
     * @param <T> Type of {@link Serializable}
     * @param file file from which to deserialize object
     * @param delete boolean true if should delete existing file after deserialization complete
     * @return ValueObject
     */
    public static <T extends Serializable> T deserialize(File file, boolean delete) {
        T vo = null;

        try {
            FileInputStream inputStream = new FileInputStream(file);
            vo = (T) SerializationUtils.deserialize(new BufferedInputStream(inputStream));

            if (delete) {
                file.delete();
            }
        } catch (FileNotFoundException e) {
            LOG.warn("File not found at location '" + file.getAbsolutePath() + "' returning null ValueObject!", e);
        } catch (SerializationException e) {
            LOG.warn("Unable to deserialize file '" + file.getAbsolutePath() + "' returning null ValueObject.", e);
        }

        return vo;
    }

    /**
     * Deserialize object from file. Delete the file after deserialization is complete. If the given filename cannot be found
     * to deserialize, null is returned.
     * 
     * @param <T> Type of {@link Serializable}
     * @param filename full pathname of file
     * @return {@link Serializable}
     */
    public static <T extends Serializable> T deserialize(String filename) {
        return (T) deserialize(filename, true);
    }

    /**
     * Deserialize an object from a byte array.
     * 
     * @param <T> Type of {@link Serializable}
     * @param bytes byte array to deserialize
     * @return {@link Serializable}
     * 
     * @see #serializeToBytes(Serializable)
     * @see SerializationUtils#deserialize(byte[])
     */
    public static <T extends Serializable> T deserializeFromBytes(byte[] bytes) {
        return (T) SerializationUtils.deserialize(bytes);
    }

    /**
     * Deserialize an object from a String.
     * <p>
     * First convert the String into a byte array by calling {@link String#getBytes()}. Then use
     * {@link SerializationUtils#deserialize(byte[])} to convert the byte array back into the original {@link Serializable}
     * object.
     * 
     * @param <T> Type of {@link Serializable}
     * @param object String to deserialize
     * @return {@link Serializable}
     * 
     * @see #serializeToString(Serializable)
     */
    public static <T extends Serializable> T deserializeFromString(String object) {
        ByteBuffer bytes = CHARSET.encode(object);

        return (T) SerializationUtils.deserialize(bytes.array());
    }

    /**
     * Deserialize object from file. If the given filename cannot be found to deserialize, null is returned.
     * 
     * @param <T> Type of {@link Serializable}
     * @param filename full pathname of file
     * @param delete boolean true if should delete existing file after deserialization complete
     * @return ValueObject
     */
    public static <T extends Serializable> T deserialize(String filename, boolean delete) {
        File file = new File(filename);

        return (T) deserialize(file, delete);
    }

    /**
     * Deserialize object from file. Delete the file after deserialization is complete. If the file in the given directory
     * with the given filename cannot be found to deserialize, null is returned.
     * 
     * @param <T> Type of {@link Serializable}
     * @param directory directory in which the file resides
     * @param filename name of file
     * @return ValueObject
     */
    public static <T extends Serializable> T deserialize(String directory, String filename) {
        return (T) deserialize(directory, filename, true);
    }

    /**
     * Deserialize object from file. If the file in the given directory with the given filename cannot be found to
     * deserialize, null is returned.
     * 
     * @param <T> Type of {@link Serializable}
     * @param directory directory in which the file resides
     * @param filename name of file
     * @param delete boolean true if should delete existing file after deserialization complete
     * @return ValueObject
     */
    public static <T extends Serializable> T deserialize(String directory, String filename, boolean delete) {
        File file = new File(directory, filename);

        return (T) deserialize(file, delete);
    }

    /**
     * Deserializes all the objects from a single directory. Delete the files after deserialization is complete.
     * 
     * @param <T> Type of {@link Serializable}
     * @param directory folder path from which to deserialize files
     * @return List of ValueObjects
     */
    public static <T extends Serializable> List<T> deserializeAll(String directory) {
        return (List<T>) deserializeAll(directory, true);
    }

    /**
     * Deserializes all the objects from a single directory. If any of the files in the given directory cannot be
     * deserialized, those objects are not placed on the resulting List.
     * 
     * @param <T> Type of {@link Serializable}
     * @param directory directory from which to deserialize files
     * @param delete boolean true if should delete existing file after deserialization complete
     * @return List of ValueObjects
     */
    public static <T extends Serializable> List<T> deserializeAll(String directory, boolean delete) {
        File folder = new File(directory);

        if (!folder.exists()) {
            LOG.warn("Directory '" + directory + "' not found in file system. Returning empty List.");

            return Collections.EMPTY_LIST;
        }

        File[] files = folder.listFiles();
        List<T> objects = new ArrayList<T>(files.length);

        for (File file : files) {
            if (file.isFile()) {
                T object = (T) deserialize(file, delete);

                if (object != null) {
                    objects.add(object);
                }
            }
        }

        return objects;
    }

    /**
     * Serializes object to file. The directory in which the file exists must already exist. Overwrites any existing file.
     * 
     * @param file {@link File} to which to serialize
     * @param object {@link Serializable} object to serialize
     * @return boolean that indicates unable to serialize due to duplicate {@link File} existing and overwrite setting.
     */
    public static boolean serialize(File file, Serializable object) {
        return serialize(file, object, true);
    }

    /**
     * Serializes object to file. The directory in which the file exists must already exist.
     * 
     * @param file {@link File} to which to serialize
     * @param object {@link Serializable} object to serialize
     * @param overwrite if file exists, overwrite?
     * @return boolean that indicates unable to serialize due to duplicate {@link File} existing and overwrite setting.
     */
    public static boolean serialize(File file, Serializable object, boolean overwrite) {
        boolean written = false;

        if (!file.exists() || overwrite) {
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                SerializationUtils.serialize(object, new BufferedOutputStream(outputStream));
                written = true;
            } catch (FileNotFoundException e) {
                throw new CommonException(e, CommonException.SERIALIZATION_ERROR, file.getAbsolutePath());
            }
        }

        return written;
    }

    /**
     * Serialize the given {@link Serializable} object to a uniquely named {@link File} in the current virtual machine's
     * temporary directory.
     * 
     * @param object {@link Serializable} object to serialize
     * @return String absolute path to the file saved
     */
    public static String serialize(Serializable object) {
        String prefix = ClassUtils.getShortClassName(object, DEFAULT_TEMPORARY_FILE_PREFIX);

        try {
            File file = File.createTempFile(prefix, null, new File(PATH));
            FileOutputStream outputStream = new FileOutputStream(file);
            SerializationUtils.serialize(object, new BufferedOutputStream(outputStream));

            return file.getAbsolutePath();
        } catch (IOException e) {
            throw new CommonException(e, CommonException.SERIALIZATION_ERROR);
        }
    }

    /**
     * Serializes object to file. The directory in which the file exists must already exist. Overwrites any existing file.
     * 
     * @param filename full path name of file to which to serialize
     * @param object {@link Serializable} object to serialize
     * @return boolean that indicates unable to serialize due to duplicate {@link File} existing and overwrite setting.
     */
    public static boolean serialize(String filename, Serializable object) {
        return serialize(filename, object, true);
    }

    /**
     * Serializes object to file. The directory in which the file exists must already exist.
     * 
     * @param filename full path name of file to which to serialize
     * @param object {@link Serializable} object to serialize
     * @param overwrite if file exists, overwrite?
     * @return boolean that indicates unable to serialize due to duplicate {@link File} existing and overwrite setting.
     */
    public static boolean serialize(String filename, Serializable object, boolean overwrite) {
        return serialize(new File(filename), object, overwrite);
    }

    /**
     * Serializes object to file. Overwrite any existing file.
     * 
     * @param directory path of directory in which to serialize file.
     * @param filename name of file to which to serialize
     * @param object {@link Serializable} object to serialize
     * @return boolean that indicates unable to serialize due to duplicate and existing and overwrite setting.
     */
    public static boolean serialize(String directory, String filename, Serializable object) {
        return serialize(directory, filename, object, true);
    }

    /**
     * Serializes object to file. The directory may or may not already exist. If it does not, the directory is created.
     * 
     * @param directory path of directory in which to serialize file.
     * @param filename name of file to which to serialize
     * @param object {@link Serializable} object to serialize
     * @param overwrite if file exists, overwrite?
     * @return boolean that indicates unable to serialize due to duplicate {@link File} existing and overwrite setting.
     */
    public static boolean serialize(String directory, String filename, Serializable object, boolean overwrite) {
        File folder = new File(directory);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        return serialize(new File(folder, filename), object, overwrite);
    }

    /**
     * Serialize an object into a byte array.
     * 
     * @param object {@link Serializable} object to serialize
     * @return byte array
     * 
     * @see #deserializeFromBytes(byte[])
     * @see SerializationUtils#serialize(Serializable)
     */
    public static byte[] serializeToBytes(Serializable object) {
        return SerializationUtils.serialize(object);
    }

    /**
     * Serialize an object into a String.
     * <p>
     * First use {@link SerializationUtils#serialize(Serializable)} to serialize the {@link Serializable} object into a byte
     * array. Then use a {@link String} constructor to convert the byte array into a String.
     * 
     * @param object {@link Serializable} object to serialize
     * @return String representation of serialized object
     * 
     * @see #deserializeFromString(String)
     */
    public static String serializeToString(Serializable object) {
        byte[] bytes = SerializationUtils.serialize(object);
        CharBuffer chars = CHARSET.decode(ByteBuffer.wrap(bytes));

        return chars.toString();
    }
}
