package services;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.enums.DataType;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(){
        return "redirect:/home";
    }


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

    @RequestMapping(value = "/home/{database}", method = RequestMethod.DELETE)
    public String deleteDatabase(HttpServletRequest request, @PathVariable("database")String database){
        DatabaseManager.getInstance().removeDatabase(database);
        return "redirect:"+request.getRequestURI().replaceAll("/"+database,"");

    }


    @RequestMapping(value = "/home/database", method = RequestMethod.POST)
    public String createDatabaseGet(@RequestParam("name") String name){
        DatabaseManager.getInstance().createDatabase(name);
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

    @RequestMapping(value = "/home/{databasename}/{table}/delete_copy", method = RequestMethod.DELETE)
    public String deleteCopy(HttpServletRequest request, @PathVariable("databasename")String databasename, @PathVariable("table")String table){
        Dispatcher.remove_copy_from_table(databasename,table);
        return "redirect:"+request.getRequestURI().replaceAll("/delete_copy","");

    }

    @RequestMapping(value = "/home/{databasename}/{table}", method = RequestMethod.DELETE)
    public String deleteTable(HttpServletRequest request, @PathVariable("databasename")String databasename, @PathVariable("table")String table){
        DatabaseManager.getInstance().getDatabaseByName(databasename).removeTable(table);
        return "redirect:"+request.getRequestURI().replaceAll("/"+table,"");

    }

    @RequestMapping(value = "/home/{databasename}/{table}/rename_column", method = RequestMethod.PUT)
    public String renameColumn(HttpServletRequest request, @PathVariable("databasename")String databasename, @PathVariable("table")String table,@RequestParam("oldValue") String oldValue, @RequestParam("newValue") String newValue){
        Dispatcher.rename_column_name(databasename,table,oldValue,newValue);
        return "redirect:"+request.getRequestURI().replaceAll("/rename_column","");

    }

    @RequestMapping(value = "/home/{databasename}/{table}/{rowIndex}", method = RequestMethod.PUT)
    public String editRow(HttpServletRequest request, @PathVariable("databasename")String databasename, @PathVariable("table")String table, @PathVariable("rowIndex") Integer rowIndex){
        Table t = DatabaseManager.getInstance().getDatabaseByName(databasename).getTableByName(table);
        List<String> newValues = new ArrayList<>();
        for(Column c : t.getColumns()){
            newValues.add(request.getParameter(c.getName()));
        }
        t.editRow(rowIndex,newValues.toArray(new String[newValues.size()]));
        return "redirect:"+request.getRequestURI().replaceAll("/"+rowIndex,"");

    }

}
