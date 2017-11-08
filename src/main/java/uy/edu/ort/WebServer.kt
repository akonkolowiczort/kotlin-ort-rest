package uy.edu.ort

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet
import uy.edu.ort.context.WebContext

class WebServer {

    companion object {

        private val DEFAULT_PORT = 8080
        private val CONTEXT_PATH = "/service"

        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val webServer = WebServer()
            webServer.run()
        }
    }

    private val server: Server

    init {
        server = Server(DEFAULT_PORT).apply {
            handler = buildContext()
            stopAtShutdown = true
        }
    }

    @Throws(Exception::class)
    private fun run() {
        with(server){
            start()
            join()
        }
    }

    @Throws(Exception::class)
    private fun buildContext() =
        ServletContextHandler().apply {
            contextPath = CONTEXT_PATH
            val applicationContext = buildApplicationContext()
            // crear dispatcher servlet de spring
            val dispatcherServlet = DispatcherServlet(applicationContext)
            addServlet(ServletHolder(dispatcherServlet), "/")

            // agregar el application context de spring a la aplicacion web
            addEventListener(ContextLoaderListener(applicationContext))

            applicationContext.close()
        }

    private fun buildApplicationContext() =
        AnnotationConfigWebApplicationContext().apply {
            register(WebContext::class.java)
        }
}
