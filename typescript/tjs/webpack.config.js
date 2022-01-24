const glob = require("glob");

const entries = glob.sync("./src/**/*.ts");


module.exports = {
  mode: "development",

  entry: entries,
  output: {
    path: `${__dirname}/dist`,
    filename: "index.js"
  },
  module: {
    rules: [
      {
        test: /\.ts$/,
        use: "ts-loader"
      }
    ]
  },
  resolve: {
    extensions: [".ts"]
  }
};
