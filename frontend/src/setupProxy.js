const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = (app) => {
    app.use(
        "/api",
        createProxyMiddleware({
            target: "http://220.86.46.100:8080",
            changeOrigin: true,
        })
    );
};