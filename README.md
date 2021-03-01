# ExpeNotes
![N|Solid](https://i.imgur.com/p0OFYLk.png)

"**ExpeNotes**" app is a type of expense notebook that allows you to set three main goals and focus on one of them (the main one). The application allows you to easily track the status of achieving our goals, but the card showing the main goal by default hides the state of current savings, all in order to surprise you with a message about the goal achieved. A possible flip card allows you to preview your current target. Archived goals are placed on the list of archived goals, and on this basis, a list of statistics of our commitment to achieving goals is created.The functionalities of the application are complemented by a currency converter that takes the current rate from the open api and presents us with the converted amount in a simple way.

# Screenshots

![N|Solid](https://i.imgur.com/u1sg3aG.jpg) ![N|Solid](https://i.imgur.com/PKR9FQG.jpg) ![N|Solid](https://i.imgur.com/dNqh41n.jpg)

# Kanban board @Trello

[![N|Solid](https://i.imgur.com/VGzwEOj.jpg)](https://trello.com/b/ENO9bgo6/expenotes)

I created my own a Kanban Board by using Trello to manage tasks and issues. Click on the above image to see the the actual version.

# Main features

  - displaying the main status of needed savings
  - the ability to define the goal as the main goal or one of the two future goals
  - setting the standard contribution amount
  - the main card for the primary objective is displayed as the front side by default
  - the ability to flip the card to see the current status of achieving the goal
  - reaching the main goal (then archiving or deleting) causes the future goal to "drag" as the main one at that moment
  - preview of archived targets in a transparent recyclerView
  - collection of statistics for tracked financial goals
  - currency converter with lots of currencies available
  - when connected to the Internet, the application calculates values based on the current exchange rate

# Under the hood

  - Room DB
  - Retrofit
  - MVVM
  - Architectural Components
  - Coroutines and Lifecycle Scopes
  - Navigation Components
  - Dagger - Hilt
  - Glide
  - Fragments
  - Shared Preferences
  - RecyclerViews
  - Bottom navigation
  - Push notifications
  - Material Design

### Installation form the Android Studio
The application requires API keys and configuration of them. Take a look at the following table:

| Service URL | Info |
| ------ | ------ |
| https://exchangeratesapi.io/ | Add /latest and download api without creating an account |

### Todos

- Fix all bugs from Kanban Board,
- Make stats counting in done fragment,
- Make card rotation and back-side layout,
- Add unit tests.

### Information from the author
> Notice that this is a very beginning version of the app and it's still developing
> so you can see some strange behaviour or some desing issues. 
> If you find some bug, please contact with me, I will try to fixed it as soon as I can.
> Other helpful tips or ideas are warmly welocome, so just let me know :)
> Thank you for visiting my repo and sharing your opinions!

