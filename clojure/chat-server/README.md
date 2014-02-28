# Clojure(Script) chat service

...for MongoDB2013 FMI course.

## Technology

### Server

- Clojure
- HTTPKit
- Monger
- Core.Async

### Client

- ClojureScript
- Om (ReactJS)
- Core.Async

## Setup

### Server
1. `cd server`
2. `lein run` - start server

### Client

1. `cd client`
2. `lein run` - watch HTML source
3. `lein cljsbuild auto dev` - watch CLJS source
4. Open compiled/index.html

For interactive development:

1. `cd client`
2. `python -m http.server ./`
3. Open index.html

## License

EPL
