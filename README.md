🧬 Description:

PharmaScan is an Android application developed in Kotlin to help combat the growing issue of counterfeit drug consumption. According to the World Health Organization, approximately 13% of all drugs worldwide are counterfeit. This app allows users to scan a drug and cross-reference it against a database to determine whether it's authentic or counterfeit.
Each scan is recorded in the user's history, with timestamp and result. The app also visualizes counterfeit drug occurrences on a heatmap, enabling pharmaceutical companies and government agencies to identify and act on high-risk regions.

⚙️ Installation & Setup:

To be able to run this project on your computer or device, here is a step-by-step instruction:
Step-1: get the link to this project from github
Step-2: clone it on your android studios
Step-3: Go to file > sync project with Gradle files
Step-4: Update your gradle if needed (not necesssary always)
Step-5: Use an emulator, pixel 4 and above are usually recommended
Step-6: Run the app

✨ Features:

PharmaScan has three main features:

First: The scanning of the drug, and showing if it is authentic or counterfeit.
<img width="188" alt="Screenshot 2025-07-07 at 3 09 49 PM" src="https://github.com/user-attachments/assets/f842048b-dbc2-48a6-84b1-92d0bf5efb25" />
<img width="183" alt="Screenshot 2025-07-07 at 3 10 28 PM" src="https://github.com/user-attachments/assets/79ad603e-957f-41f9-87af-c950c9e04275" />
<img width="183" alt="Screenshot 2025-07-07 at 3 10 51 PM" src="https://github.com/user-attachments/assets/e9d3d4a2-9c00-48ab-8484-6e6ac3e04d30" />

Second: The history of the scanned drugs, with date and time, and also if the drug was authentic or counterfeit.
<img width="187" alt="Screenshot 2025-07-07 at 3 11 49 PM" src="https://github.com/user-attachments/assets/127635fa-1d1f-473a-b557-6d491448f1ad" />

Third: It will show you on the map, where the counterfeit drug has been found.
<img width="183" alt="Screenshot 2025-07-07 at 3 12 41 PM" src="https://github.com/user-attachments/assets/4a288cd9-4143-49c5-9517-804d693d3a41" />

🧰 Technology Stack:

Kotlin - Primary Programming Language for android development
Android Jetpack components (ViewModel, LiveData, Navigation)
Room — local database for storing drug and scan reports
Google Maps API — for map and heatmap visualization
Coroutines — for asynchronous operations
CameraX API — for capturing images
Git — version control
Android Studio — IDE

📽️ Demo Video:

https://github.com/user-attachments/assets/f4ed9481-1ef1-4c49-b5d2-43a06af09192

🚧 Future Development Roadmap:

Moving forward, there are a lot of implementations that need to take place, here is the next 6 month roadplan to ensure this app is completed to the maximum degree.

Goal 1 - Blockchain-Backed Databse
To change the Room database, to a blockchain focused memroy allocation and data storage, as the drugs being stored need to be completely immutable once placed inside. To accomplsh this goal, I must learn the implementation and utilization of blockchain, and understand how to integrate with the applications.

Goal 2 - Regional Drug Catalog 
Store all the medical drug data, in the application, based on region, and their drugs used, and find a prominent way to segregate similar drugs from different regions, and banned drugs at a country X, but is widely used in a country Y, and how to handle the data migration and communication within the system.

Goal 3 ->  Beta Testing
Run a Beta version, and see how much data can be obtained on counterfeit drugs, and then move forward based on the utilization of the application, and data obtained through the scans.



Contact Information:
Name: Pranish Somyajula
Email: Pranish.somyajula15@gmail.com












