# Finanting

## Information

A financial accounting application.

The application is developed in Java (Spring Boot) for the backend and React for the frontend.

To manage the full application we used maven.

A docker-file to generate the development environment is available. The different SQL query are available to prepare the database.

Finally, a vagrant file are available to prepare a VM with docker and the database.

## Requirements (development)

* Java = 11
* Maven >= 3.6.3

## Requirements (run)

* MySQL = 8.0.20

## Build

To build used the command : `mvn package`

## Pull requests

When you create a pull request, a series of actions will launch.
To merge the pull request, all actions must be succeeded.

One of the action is a static check of the code. We used PMD (https://pmd.github.io/), ESlint and Prettier.

To check the code :
- frontend : run the command `npm run lint` and `prettier:check`
- server : run the command `mvn pmd:pmd` and check if the file pmd.html on the folder `target/site` exist.

**All remarks must be taken into account**

Finally, a manual validation of the pull request is necessary.

# OPENAPI

To generate the code with the OpenApi CLI go to `openapi` folder.
Launch :
- `npm run delete` to delete old generation
- `npm run generate` to generate the code
- `npm run copy` to copy the code on the frontend and server folder

# FRONTEND 

# Getting Started with Create React App

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.\
You will also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can’t go back!**

If you aren’t satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you’re on your own.

You don’t have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn’t feel obligated to use this feature. However we understand that this tool wouldn’t be useful if you couldn’t customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).
