package softwareengineering;

import static projectdb.ProjectDB.getDollarsPerPoint;
import static projectdb.ProjectDB.getPointsPerDollar;

public class settingsData {
    public double pointsPerDollar;
    public double dollarsPerPoint;
    
    public settingsData() {
        pointsPerDollar = getPointsPerDollar("admin");
        dollarsPerPoint = getDollarsPerPoint("admin");
    }
}