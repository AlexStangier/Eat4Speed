module.exports = {
  "transpileDependencies": [
    "vuetify",
    'vuex-persist'
  ],
  outputDir: 'build/META-INF/resources',
  publicPath: '',
  devServer: {
    proxy: 'http://localhost:1337',
  }
}