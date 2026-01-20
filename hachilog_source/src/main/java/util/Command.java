package util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command { // dao 메서드를 호출하여 CRUD 작업처리를 위한 설계도
	
	void doCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
}
