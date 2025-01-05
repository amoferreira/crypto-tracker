# crypto-tracker

App for tracking crypton market, which consists of a list screen with a large number of cryto coins and a detail screen for each of them, including a hystorical price graphic. The adaptive navigation allows to have 2 separate screens (coins list and details) in portrait configuration and 1 single screen in lanscape configuration.

**App screens**
![Cryto-tracker](https://github.com/user-attachments/assets/255cf481-2d42-4c23-935d-b9629677a5e1)

**Demo video**
[Crypto-Tracker-Demo.webm](https://github.com/user-attachments/assets/9e564432-5259-44a3-8147-da36ee3f82cb)

This app was developed based on the The Best Practice Guide to Android Architecture course by Phillip Lackner. The app architeture consists of a core and crypto packages in the root, each one with a data, domain and presentation layers inside, and a di package (koin was used as dependency injection framework).
