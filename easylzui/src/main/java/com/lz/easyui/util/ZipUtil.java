package com.lz.easyui.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	public static byte[] deflate(byte[] content) {
	    byte[] result = null;
		if (content != null) {
			ByteArrayOutputStream temp = new ByteArrayOutputStream();
			DeflaterOutputStream compresser = new DeflaterOutputStream(temp);
			try {
				compresser.write(content);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(compresser);
			}
			result = temp.toByteArray();
		}
		return result;
	}

	public static byte[] inflate(InputStream input) {
		byte[] result = null;
		InflaterInputStream decompresser = null;
		try {
			decompresser = new InflaterInputStream(input);
			result = IOUtils.toByteArray(decompresser);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(decompresser);
		}
		return result;
	}

	public static void zip(File src, File zip) throws IOException {
		zip(src, FileUtils.openOutputStream(zip));
	}

	public static void zip(File src, OutputStream out) throws IOException {
		zip(src, new ZipOutputStream(out));
	}

	public static void zip(File src, ZipOutputStream zout) throws IOException {
		try {
			doZip(src, zout, "");
		} finally {
			IOUtils.closeQuietly(zout);
		}
	}

	public static void unzip(File zip, File dest) throws IOException {
		unzip(FileUtils.openInputStream(zip), dest);
	}

	public static void unzip(InputStream in, File dest) throws IOException {
		unzip(new ZipInputStream(in), dest);
	}

	public static void unzip(ZipInputStream zin, File dest) throws IOException {
		try {
			doUnzip(zin, dest);
		} finally {
			IOUtils.closeQuietly(zin);
		}
	}

	private static void doZip(File src, ZipOutputStream zout, String ns)
			throws IOException {
        if (src.isFile()) {
            String entryName = ns + src.getName();
            zout.putNextEntry(new ZipEntry(entryName));
            fillZip(FileUtils.openInputStream(src), zout);
        } else {
            for (File file : src.listFiles()) {
                String entryName = ns + file.getName();

                if (file.isDirectory()) {
                    zout.putNextEntry(new ZipEntry(entryName));
                    doZip(file, zout, entryName);
                } else {
                    zout.putNextEntry(new ZipEntry(entryName));
                    fillZip(FileUtils.openInputStream(file), zout);
                }
            }
        }
	}

	private static void doUnzip(ZipInputStream zin, File dest)
			throws IOException {
		for (ZipEntry e; (e = zin.getNextEntry()) != null; zin.closeEntry()) {
			File file = new File(dest, e.getName());

			if (e.isDirectory()) {
				FileUtils.forceMkdir(file);
			} else {
				flushZip(zin, FileUtils.openOutputStream(file));
			}
		}
	}

	private static void fillZip(InputStream in, ZipOutputStream zout)
			throws IOException {
		try {
			IOUtils.copy(in, zout);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	private static void flushZip(ZipInputStream zin, OutputStream out)
			throws IOException {
		try {
			IOUtils.copy(zin, out);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

    public static byte[] unZip(byte[] bContent)
    {
        byte[] b = null;
        try
        {
            ByteArrayInputStream bis = new ByteArrayInputStream(bContent);
            ZipInputStream zip = new ZipInputStream(bis);
            while (zip.getNextEntry() != null)
            {
                byte[] buf = new byte[1024];
                int num = -1;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((num = zip.read(buf, 0, buf.length)) != -1)
                {
                    baos.write(buf, 0, num);
                }
                b = baos.toByteArray();
                baos.flush();
                baos.close();
            }
            zip.close();
            bis.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return b;
    }


}
