## Development

This is knowledge discovery. I think this is how it works:

* use vscode for development
* use Android Studio to deploy to the device

So, now, I need to find the commands to actually do the things. Firstly, how do I run this? I think I need
to build the scalajs file and then just go to the browser. Let's test that theory


When running with just HTML and not worrying about the app deployment, we use:

```
# Builds a JS file in target/scala-2.13/scala-js-tutorial-fastopt/main.js
# This will not update the file if there are no scala changes.
$ sbt fastLinkJS
```

Then to view the desktop application, browse to `index.html`. It will point directly to that js file.
Use `index-maze.html` for Maze.

> Note, to get continuous compilation, you can also use `sbt ~fastLinkJS`

## Mobile App Deployment

Firstly, you'll want a production build

```
$ sbt fullLinkJS
```

This will generate a file in `target/scala-2.13/scala-js-tutorial-opt/main.js`.

Then, you'll need to copy the index files and this js file to picasso-app and put it in `picasso-app/app/src/main/assets`. Then, make sure the `WebView` in `MainActivity` is referencing the right HTML file, and that the HTML file is referencing the right js file. You will need to modify it.

### Deploying

Use Android Studio

**Which User do you need to be?**

Admin. Deploy while Admin. If you can't see the device in Android Studio, find a better cable. Has to have data capabilities.
