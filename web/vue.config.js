const webpack = require('webpack')

module.exports = {
    configureWebpack:{
        plugins:[
            new webpack.ProvidePlugin({
                $: 'jquery',
                jQuery: 'jquery',
                "window.jQuery":"jquery",
                "window.Quill":'quill',
                Popper:["popper.js","default"]
            })
        ]
    },
    devServer:{
        port:3000
    }
}

