export function initializeChannelIO() {
  (function () {
    var w = window;
    if (w.ChannelIO) {
      return (window.console.error || window.console.log || function () {})(
        "ChannelIO script included twice."
      );
    }
    var ch = function () {
      ch.c(arguments);
    };
    ch.q = [];
    ch.c = function (args) {
      ch.q.push(args);
    };
    w.ChannelIO = ch;
    function l() {
      if (w.ChannelIOInitialized) {
        return;
      }
      w.ChannelIOInitialized = true;
      var s = document.createElement("script");
      s.type = "text/javascript";
      s.async = true;
      s.src = "https://cdn.channel.io/plugin/ch-plugin-web.js";
      var x = document.getElementsByTagName("script")[0];
      x.parentNode.insertBefore(s, x);
    }
    if (document.readyState === "complete") {
      l();
    } else if (window.attachEvent) {
      window.attachEvent("onload", l);
    } else {
      window.addEventListener("DOMContentLoaded", l, false);
      window.addEventListener("load", l, false);
    }
  })();
  ChannelIO("boot", {
    pluginKey: "84895382-fb41-404c-84d0-7d43d378af47",
  });
}
