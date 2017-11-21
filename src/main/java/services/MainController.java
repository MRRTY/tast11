package services;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.enums.DataType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class MainController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject(DatabaseManager.getInstance().getLIST_DATABASE());
        return modelAndView;

    }
    @RequestMapping(value = "/home/{database}", method = RequestMethod.GET)
    public ModelAndView viewDatabase(@PathVariable("database")String database){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("database");
        modelAndView.addObject(DatabaseManager.getInstance().getDatabaseByName(database));
        return modelAndView;

    }


    @RequestMapping(value = "/home/database", method = RequestMethod.POST)
    public String createDatabaseGet(@RequestParam("name") String name){
        Dispatcher.create_database(new String[]{name});
        return "redirect:/home";
    }
    @RequestMapping(value = "/home/{databasename}/{table}", method = RequestMethod.GET)
    public ModelAndView viewTable(@PathVariable("databasename")String databasename,@PathVariable("table")String table){
        Database database = DatabaseManager.getInstance().getDatabaseByName(databasename);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("table");
        modelAndView.addObject(database.getTableByName(table));
        return modelAndView;
    }

    @RequestMapping(value = "/home/{databasename}/{table}/addRow", method = RequestMethod.POST)
    public String addRow(HttpServletRequest request, @PathVariable("databasename")String databasename, @PathVariable("table")String table){
        Database database = DatabaseManager.getInstance().getDatabaseByName(databasename);
        Table t = database.getTableByName(table);
        List<String> values = new ArrayList<>();
        for(Column c : t.getColumns()){
            values.add(request.getParameter(c.getName()));
        }
        t.addRow(values);
        return "redirect:"+request.getRequestURI().replaceAll("/addRow","");

    }

    @RequestMapping(value = "home/{database}/table",method = RequestMethod.POST)
    public String createTable(HttpServletRequest request, @RequestParam("dbName")String dbName,@RequestParam("tableName")String tableName,@RequestParam("command")String command){
        Database currentDatabase = DatabaseManager.getInstance().getDatabaseByName(dbName);
        Map<String,DataType> columns = new HashMap<>();
        String[] commandLine = command.split(" ");
        System.out.println(Arrays.toString(commandLine));
        for (int i = 0; i<(commandLine.length)/2; i++){
            columns.put(commandLine[2*(i)],Dispatcher.getDatatype(commandLine[2*(i)+1]));
        }
        currentDatabase.addTable(tableName,columns);
        return "redirect:"+request.getRequestURI().replaceAll("/table","");

    }


}
