import { sentryWebpackPlugin } from "@sentry/webpack-plugin";
import { CleanWebpackPlugin } from "clean-webpack-plugin";
import dotenv from "dotenv";
import HtmlWebpackPlugin from "html-webpack-plugin";
import path from "path";
import webpack from "webpack";

dotenv.config();

export default (env, argv) => {
  const prod = argv.mode === "production";

  return {
    mode: prod ? "production" : "development",
    devtool: prod ? "hidden-source-map" : "eval",
    entry: "./src/index.tsx",
    output: {
      path: path.join(path.resolve(), "/dist"),
      filename: "index.[contenthash].js",
      publicPath: "/",
    },
    devServer: {
      historyApiFallback: true,
      port: 3000,
      hot: true,
    },
    resolve: {
      extensions: [".js", ".jsx", ".ts", ".tsx", ".json", ".mjs"],
      alias: {
        "@": path.resolve(path.resolve(), "src/"),
      },
    },
    module: {
      rules: [
        {
          test: /\.tsx?$/,
          use: ["ts-loader"],
        },
        {
          test: /\.(png|svg|jpg|jpeg|gif)$/i,
          type: "asset/resource",
        },
      ],
    },
    plugins: [
      new webpack.DefinePlugin({
        "process.env": JSON.stringify(process.env),
      }),
      new webpack.ProvidePlugin({
        React: "react",
      }),
      new HtmlWebpackPlugin({
        template: "./public/index.html",
        minify:
          process.env.NODE_ENV === "production"
            ? {
                collapseWhitespace: true, // 빈칸 제거
                removeComments: true, // 주석 제거
              }
            : false,
      }),
      new CleanWebpackPlugin(),
      sentryWebpackPlugin({
        authToken: process.env.SENTRY_AUTH_TOKEN,
        org: "corea",
        project: "2024-corea",
        ignore: ["node_modules", "webpack.config.js"],
        sourcemaps: {
          filesToDeleteAfterUpload: "**/*.js.map",
        },
      }),
    ],
  };
};
