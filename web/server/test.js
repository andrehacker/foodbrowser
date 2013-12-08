var http = require("http");

http.createServer(function(request, response) {
  response.writeHead(200, {"Content-Type": "text/plain"});
  console.log("Request: " + request);
  response.write("Hello World");
  response.end();
}).listen(8888);