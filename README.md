# Software-Engineering
JavaMail 1.6.2 API Download: https://github.com/javaee/javamail/releases
In NetBeans, open the project explorer, expand the project and right click the "Libraries" Folder. Click "Add JAR/Folder" and select the downloaded JAR file. The code should compile after these steps.

If you get an error saying "Bad Credentials" using a gmail account, you must have "Allow Less Secure Apps" option turned on in your gmail settings. Do this for now until I can implement the use app password for a more secure way of using a gmail account. For the purpose of this project though, we might just use a fake email account just for showing functionality. 

We are also planning on using GSON, Google's JSON library, to handle information transfer from the frontend to the backend and vice versa. To download that, use the following link and click downloads in the right hand corner and select .jar: https://search.maven.org/artifact/com.google.code.gson/gson/2.8.6/jar

To link it in NetBeans, it's the exact same instructions as above, add it as a jar to your project libraries.
