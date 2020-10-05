package controllers;

import play.mvc.Controller;
import play.mvc.Result;

import java.io.File;

public class AssetController extends Controller {
    public Result at(String filePath) {
        File file = new File(filePath);
        return ok(file, true);
    }

    /*public Result att() {
        return
    }*/

}