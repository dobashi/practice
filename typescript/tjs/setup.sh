npm init
npm i -D webpack webpack-cli typescript ts-loader
echo << EOF > tsconfig.json
{
  "compilerOptions": {
    "sourceMap": true,
    "target": "es5", 
    "module": "es2015",
    "moduleResolution": "node",
    "lib": [ "es2019", "dom" ]
  }
}
EOF
echo << EOF > webpack.config.js
module.exports = {
  mode: "development",

  entry: "./src/main.ts",
  output: {
    path: `${__dirname}/dist`,
    filename: "main.js"
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
EOF
npm i -D webpack webpack-cli typescript ts-loader
npm i -S three @types/three
