// Karma configuration
// Generated on Mon Aug 04 2014 05:10:16 GMT-0700 (PDT)

module.exports = function (config) {
    config.set({

        // base path that will be used to resolve all patterns (eg. files, exclude)
        basePath: 'src',


        // frameworks to use
        // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
        frameworks: ['jasmine'],


        // list of files / patterns to load in the browser
        files: [
            'main/webapp/js/lib/jquery-2.1.1.js',
            'main/webapp/js/util/*.js',
            'main/webapp/js/*.js',
            'test/webapp/js/*.js'
        ],

        plugins: [
            'karma-phantomjs-launcher',
            'karma-jasmine',
	    'karma-junit-reporter'
        ],


        // test results reporter to use
        // possible values: 'dots', 'progress'
        // available reporters: https://npmjs.org/browse/keyword/karma-reporter
        reporters: ['progress', 'junit'],
	

	// the default configuration
	junitReporter: {
		outputFile: '../test-results.xml',
		suite: ''
	},	

        // web server port
        port: 9876,


        // enable / disable colors in the output (reporters and logs)
        colors: true,


        // level of logging
        // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
        logLevel: config.LOG_DEBUG,


        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: false,


        // start these browsers
        // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
        browsers: ['PhantomJS'],


        // Continuous Integration mode
        // if true, Karma captures browsers, runs the tests and exits
        singleRun: true


    });
};
