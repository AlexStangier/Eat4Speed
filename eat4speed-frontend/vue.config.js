module.exports = {
  "transpileDependencies": [
    "vuetify",
    'vuex-persist'
  ],
  outputDir: '../resources/META-INF/resources',
  publicPath: '',
  devServer: {
    proxy: 'http://localhost:1337',
  }
}