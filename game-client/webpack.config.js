const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
  target: 'web',
  entry: {'babel-polyfill':'./modules/gobang/js/main.js'},
  output: {
    path: path.resolve(__dirname, './dist'),
    filename: 'modules/gobang/js/main.min.js',
  },
  mode: 'production',
  module: {
    rules: [{
      test: /\.js$/,
      loader: 'babel-loader',
      exclude: path.resolve(__dirname, 'node_modules'),
    },
    {
      test: /\.worker\.js$/,
      loader: 'worker-loader',
      options: {
        name: '[name]:[hash:8].js',
        fallback: false,
        inline: true,
      },
    },
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({
      title: '五子棋',
      filename: 'modules/gobang/page/index.html',
      template: path.resolve(__dirname, 'modules', 'gobang/page/index.html'),
      inject: 'head',
      favicon: false,
      /*//部分省略，具体看minify的配置
      minify: {
        //是否对大小写敏感，默认false
        caseSensitive: true,
        //是否简写boolean格式的属性如：disabled="disabled" 简写为disabled  默认false
        collapseBooleanAttributes: true,
        //是否去除空格，默认false
        collapseWhitespace: true,
        //是否压缩html里的css（使用clean-css进行的压缩） 默认值false；
        minifyCSS: true,
        //是否压缩html里的js（使用uglify-js进行的压缩）
        minifyJS: true,
        //Prevents the escaping of the values of attributes
        preventAttributesEscaping: true,
        //是否移除属性的引号 默认false
        removeAttributeQuotes: true,
        //是否移除注释 默认false
        removeComments: true,
        //从脚本和样式删除的注释 默认false
        removeCommentsFromCDATA: true,
        //是否删除空属性，默认false
        removeEmptyAttributes: true,
        //  若开启此项，生成的html中没有 body 和 head，html也未闭合
        removeOptionalTags: false,
        //删除多余的属性
        removeRedundantAttributes: true,
        //删除script的类型属性，在h5下面script的type默认值：text/javascript 默认值false
        removeScriptTypeAttributes: true,
        //删除style的类型属性， type="text/css" 同上
        removeStyleLinkTypeAttributes: true,
        //使用短的文档类型，默认false
        useShortDoctype: true,
      },*/
    }),
    new CopyWebpackPlugin([{
      from: path.resolve(__dirname, 'modules'),
      to: './modules',
      ignore: ['*js'],
    }]),
  ],
};
