module.exports = {
  "transpileDependencies": [
    "vuetify",
    'vuex-persist'
  ],
  outputDir: '../eat4speed-backend/src/main/resources/META-INF/resources',
  publicPath: '',
  devServer: {
    proxy: 'http://localhost:1337',
  }
}