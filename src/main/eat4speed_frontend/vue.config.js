module.exports = {
  "transpileDependencies": [
    "vuetify"
  ],
  outputDir: '../resources/META-INF/resources',
  publicPath: '',
  devServer: {
    proxy: 'http://localhost:1337',
  }
}