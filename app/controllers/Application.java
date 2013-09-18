package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import play.data.*;
import models.*;

import play.libs.F.*;
import org.codehaus.jackson.*;


public class Application extends Controller {
  
  static Form<Task> taskForm = Form.form(Task.class);
  static Form<FormData> dataForm = Form.form(FormData.class);

    public static Result index() {
//        return ok(index.render("Your new application is already ready."));
        return redirect(routes.Application.tasks());
    }
  
	public static Result tasks() {
		return ok(
			views.html.index.render(Task.all(), taskForm)
		);
	}

	public static Result newTask() {
	  Form<Task> filledForm = taskForm.bindFromRequest();
        if (filledForm.hasErrors()) {
		return badRequest(views.html.index.render(Task.all(), filledForm));
	  } else {
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart resourceFile = body.getFile("picture");

/*		
		if (resourceFile == null) {
			return badRequest(views.html.index.render(Task.all(), filledForm));
		} 
*/

		Task.create(filledForm.get());
		return redirect(routes.Application.tasks());  
	  }
	}

	public static Result deleteTask(Long id) {
	  Task.delete(id);
	  return redirect(routes.Application.tasks());
	}
	
	

    public static Result textarea() {
        Form<FormData> filledForm = dataForm.bindFromRequest();

        int col = 12;

        String text = filledForm.get().textAreaMessage;
        int length = text.length();
        int visibleLines = length/col + 1;

        return ok(textarea.render(text, visibleLines, 0, length));
    }

    public static WebSocket<String> websock() {
        return new WebSocket<String>() {

            // Called when the Websocket Handshake is done.
            public void onReady(WebSocket.In<String> in, final WebSocket.Out<String> out) {

                // For each event received on the socket,
                in.onMessage(new Callback<String>() {
                    public void invoke(String event) {
                        out.write(event);
                    }
                });

                // When the socket is closed.
                in.onClose(new Callback0() {
                    public void invoke() {
                    }
                });

                out.write("Started websocket!");
            }

        };
    }
	
}
