# Software-Engineering
This is the multipurpose online cash registers that can be used for stores. It consists of a point system, sms login, admin and merchant panels, daily, weekly, and monthly sales reports, and much more. The front end was developed using HTML, CSS, and Javascript where we sent JSON objects to the HTTP server backend coded in java, and converted those into java objects for front and back communication. 

This project was made using the agile iterative software development life cycle plan. We only completed two iterations. 

API and Libraries INFO
JavaMail 1.6.2 API Download: https://github.com/javaee/javamail/releases
In NetBeans, open the project explorer, expand the project and right click the "Libraries" Folder. Click "Add JAR/Folder" and select the downloaded JAR file. The code should compile after these steps.

If you get an error saying "Bad Credentials" using a gmail account, you must have "Allow Less Secure Apps" option turned on in your gmail settings. Do this for now until I can implement the use app password for a more secure way of using a gmail account. For the purpose of this project though, we might just use a fake email account just for showing functionality. 

We are also planning on using GSON, Google's JSON library, to handle information transfer from the frontend to the backend and vice versa. To download that, use the following link and click downloads in the right hand corner and select .jar: https://search.maven.org/artifact/com.google.code.gson/gson/2.8.6/jar

To link it in NetBeans, it's the exact same instructions as above, add it as a jar to your project libraries.
