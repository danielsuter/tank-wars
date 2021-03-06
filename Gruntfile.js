module.exports = function(grunt) {
// Load Grunt tasks declared in the package.json file
    require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);



// Configure Grunt
    grunt.initConfig({

// Grunt express - our webserver
// https://github.com/blai/grunt-express
        express: {
            all: {
                options: {
                    bases: ['src/main/webapp/'],
                    port: 9090,
                    hostname: "0.0.0.0",
                    livereload: true
                }
            }
        },

// grunt-watch will monitor the projects files
// https://github.com/gruntjs/grunt-contrib-watch
        watch: {
            all: {
                files: ['**/*.html', '**/*.js'],
                options: {
                    livereload: true
                }
            }
        },

        karma: {
            unit: {
                configFile: 'karma.conf.js'
            }
        },

// grunt-open will open your browser at the project's URL
// https://www.npmjs.org/package/grunt-open
        open: {
            all: {
                path: 'http://localhost:9090/index.html'
            }
        }
    });


    // Creates the `server` task
    grunt.registerTask('server', [
        'express',
        'open',
        'watch'
    ]);

    grunt.registerTask('test', [
        'karma'
    ]);
};
