(ns chat-server.texts)

(def texts {
            ;; Errors
            :err-http "HTTP connections are not supported. Please use WebSockets."
            :err-parsing "Exception when parsing message: "
            :err-running "Server is already running"
            :err-unknown-type "Unknown message type: "
            :err-not-pending "You are not in pending list."
            :err-wrong-user "Wrong username or password."
            :err-auth-required "You must authenticate before sending post messages."

            ;; Prompts
            :prompt-name "Enter your name: "

            ;; Other
            :logged " logged."
            })
