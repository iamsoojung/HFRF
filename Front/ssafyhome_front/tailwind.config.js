module.exports = {
  content: ["./src/**/*.{vue, js}",
            "./node_modules/flowbite/**/*.js"],
  theme: {
    extend: {
      fontFamily: {
        'Pretendard Variable':['Pretendard Variable']
      },
    },
  },
  plugins: [
    require('flowbite/plugin')
  ],
}
