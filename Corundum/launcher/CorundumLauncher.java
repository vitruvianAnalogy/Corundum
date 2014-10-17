package Corundum.launcher;

import Corundum.Corundum;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CorundumLauncher {
    /** This is the main method of Corundum. When Corundum is started, this is the method that is called to start the program.
     * 
     * @param arguments
     *            is the list of <tt>String</tt> arguments given in the command to start this program. These arguments are given through a command line like the Windows
     *            command prompt or Unix terminal.<br>
     * <br>
     *            Accepted arguments:<br>
     *            <i>debugging</i>:<br>
     *            <li><tt>--debug, -d</tt> to activate debugging mode, which outputs debugging information to the console and the logs</li> <li><tt>--no-debug, -D</tt> to
     *            deactivate debugging mode</li> <li><tt>--verbose, -v</tt> to activate verbose debugging mode, which outputs lots of extra detailed debugging information to
     *            the console and the logs</li> <li><tt>--no-verbose, -V</tt> to deactivate verbose debugging mode</li> <i>server properties <b>(not yet supported)</b></i>: <br>
     *            <li>
     *            <tt>--online-mode, -o</tt> to activate online mode, which makes the server connect to Mojang's authentication servers and verify users when they try to
     *            connect to the server</li> <li><tt>--offline-mode, -O</tt> to deactivate online mode</li> <li><tt>--world=[WORLD]</tt> to specify the name of your main world
     *            (usually the Overworld); leave it blank to specify the default world name, "world"</li>
     *            <i>optional features</i>:<br>
     *            <li><tt>--use-experimental-loader</tt> activates the experimental CorundumLoader. To the end user there's little difference,
     *            but internally, the loader is changed to be more modularised as opposed to being hardcoded. </li>
     *            */
    public static void main(String[] arguments) {
        // Downloads the minecraft_server jar in case it's not already in the directory.
        downloadMCServerJar(new File("."));

        @SuppressWarnings("resource")
        URLClassLoader corundumLoader;
        if (!Arrays.asList(arguments).contains("--use-experimental-loader")) {
            // load the Minecraft server jar
            System.out.println("Loading the Minecraft server jar...");
            @SuppressWarnings("resource")
            URLClassLoader minecraftLoader = loadJar("minecraft_server.jar", CorundumLauncher.class.getClassLoader(), true);
            if (minecraftLoader == null)
                return;
            System.out.println("Success!");

            // load the Corundum jar
            System.out.println("Loading the Corundum server jar...");
            corundumLoader = loadJar("Corundum.jar", minecraftLoader);
            if (corundumLoader == null) {
                try {
                    minecraftLoader.close();
                } catch (IOException exception) {
                    System.out.println("I was unable to close the Minecraft jar ClassLoader after Corundum's loading failed!");
                    exception.printStackTrace();
                }
                return;
            }
            System.out.println("Success!");
        } else {
            //Use the CorundumLoader instead of the other method..
            corundumLoader = new CorundumLoader(new URL[0], CorundumLauncher.class.getClassLoader());
            ((CorundumLoader) corundumLoader).loadJars();
        }

        // start Corundum
        Class<?> mainClass;
        try {
            mainClass = Class.forName("Corundum.Corundum", true, corundumLoader);
            mainClass.getMethod("main", String[].class).invoke(null, (Object) arguments);
        } catch (ClassNotFoundException | IllegalArgumentException | SecurityException | IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
            System.out.println("I couldn't load the Corundum main class!");
            exception.printStackTrace();
        }
    }

    /**
     * Downloads the minecraft server jar.
     * @param outDir The directory to output the jar to.
     */
	public static void downloadMCServerJar(File outDir) {
		try {
            File outJar = new File(outDir, "minecraft_server.jar");

            if (!outJar.exists() && outDir.isDirectory()) {
                URL mcServerDownload = new URL("https://s3.amazonaws.com/Minecraft.Download/versions/1.7.10/minecraft_server.1.7.10.jar");
                HttpURLConnection downloadUrlConnection = (HttpURLConnection) mcServerDownload.openConnection();
                //A Scanner is an easier way to read a stream than the while ((var = stream.read()) != -1) method,
                //in my opinion. It's also neater.
                Scanner streamScanner = new Scanner(downloadUrlConnection.getInputStream());
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outJar));

                while (streamScanner.hasNextByte()) {
                    outputStream.write(streamScanner.nextByte());
                }
            }
		} catch (IOException exception) {
           exception.printStackTrace();
        }
	}

    /** This method loads all the classes in a given jar file.
     * 
     * @param file_path
     *            is the file path to the jar to be loaded.
     * @param parent
     *            is the {@link ClassLoader} that will be used as a parent to the {@link ClassLoader} that will be used to load the jar and be returned at the end of this
     *            method. It is important that you choose your parent {@link ClassLoader}s wisely; a child {@link ClassLoader} can use all the classes loaded by its parent
     *            {@link ClassLoader}, even if both {@link ClassLoader}s are loading from different files or {@link URL}s.
     * @return the {@link URLClassLoader} used to load the classes inside the specified jar file or <b>null</b> if there was an error. */
    public static URLClassLoader loadJar(String file_path, ClassLoader parent) {
        return loadJar(file_path, parent, false);
    }

    @SuppressWarnings("resource")
    private static URLClassLoader loadJar(String file_path, ClassLoader parent, boolean is_Minecraft_server_jar) {
        JarFile jar;
        try {
            jar = new JarFile(file_path);
        } catch (FileNotFoundException exception) {
            System.out.println("I couldn't find " + file_path + "!");
            exception.printStackTrace();
            return null;
        } catch (IOException exception) {
            System.out.println("There was a problem loading " + file_path + "!");
            exception.printStackTrace();
            return null;
        }

        URLClassLoader loader;
        try {
            loader = new URLClassLoader(new URL[] { new URL("jar:file:" + file_path + "!/") }, parent);
        } catch (MalformedURLException exception) {
            try {
                jar.close();
            } catch (IOException exception2) {
                System.out.println("I was unable to close " + file_path + " when this " + exception.getClass().getSimpleName() + " popped up!");
                exception2.printStackTrace();
            }
            System.out.println("The URL for this jar file was malformed!");
            System.out.println("file path=" + file_path);
            exception.printStackTrace();
            return null;
        }

        Enumeration<JarEntry> e = jar.entries();
        for (JarEntry entry = e.nextElement(); e.hasMoreElements(); entry = e.nextElement())
            if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                String class_name = entry.getName().substring(0, entry.getName().length() - 6).replaceAll("/", "");
                try {
                    loader.loadClass(class_name);
                } catch (NoClassDefFoundError | ClassNotFoundException exception) {
                    System.out.println("failed to load " + class_name);
                    
                    /* if the Minecraft server jar threw the error, just ignore it; there are classes in the Minecraft server jar that reference other non-existent classes, so
                     * we'll just have to deal with it and ignore it since we can't change the Minecraft server jar */
                    if (is_Minecraft_server_jar)
                        continue;

                    try {
                        loader.close();
                    } catch (IOException exception2) {
                        System.out.println("I was unable to close " + class_name + "'s loader when this " + exception.getClass().getSimpleName() + " popped up!");
                        exception2.printStackTrace();
                    }
                    try {
                        jar.close();
                    } catch (IOException exception2) {
                        System.out.println("I was unable to close " + file_path + "'s jar when " + class_name + " failed to load!");
                        exception2.printStackTrace();
                    }
                    exception.printStackTrace();
                    return null;
                }
            }

        // close the ClassLoader (which will not affect access to the loaded classes)
        try {
            loader.close();
        } catch (IOException exception) {
            try {
                jar.close();
            } catch (IOException exception2) {
                System.out.println("I couldn't close the jar while failing to close the loader after loading " + file_path + "!");
                exception2.printStackTrace();
            }
            System.out.println("I couldn't close the loader after loading " + file_path + "!");
            exception.printStackTrace();
            return null;
        }

        // close the JarFile (which will also not affect access to the loaded classes)
        try {
            jar.close();
        } catch (IOException exception) {
            System.out.println("I couldn't close the jar after loading " + file_path + "!");
            exception.printStackTrace();
        }

        System.out.println("loaded " + file_path);

        return loader;
    }
}
