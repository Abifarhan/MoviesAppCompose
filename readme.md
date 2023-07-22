BDD Specs
AEJ Case Study
Watch List Feature Specs

Story: User requests to see watch list movie

Narative #1
As a online user
I want the app to automatically load the watch list movies
So I can see the watch list movies

Scenarios #1
Given the user has connectivity
When the user requests to see the watch list movies
Then the app should display the watch list movies from remote

UX goals for the watch list movies UI experience:
[ ] Load watch list automatically when view is presented
[ ] Show a loading indicator when watch list load automatically(you can use shimmer or something else)
[ ] Show a empty state when watch list data is empty and add button to retry mechanism
[ ] Render all loaded watch list items (image, title)
[ ] Show a network error state when app doesn’t have connectivity
[ ] Allow user to manually retry mechanism when doesn’t have connectivity
[ ] Allow user to manually reload watch list (swipe to refresh)
https://developer.themoviedb.org/reference/intro/getting-started
