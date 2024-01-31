/* Adapted From 
 * 
 * https://github.com/jfree/jfree-demos/blob/master/src/main/java/org/jfree/chart/demo/PieChartDemo1.java
 *
 * and
 *
 * http://www.jfree.org/phpBB2/viewtopic.php?f=3&t=25969
 */
/* ----------------------------
* LegendTitleToImageDemo2.java
* ----------------------------
* (C) Copyright 2008, by Object Refinery Limited.
*
*/

package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.Range;

/**
* Here we save a legend to a PNG file...the legend has a lot of items, so we
* apply a width constraint so it doesn't get too wide.
*/
public class ChartTest2 {

  private static JFreeChart createChart(PieDataset dataset) {

	  JFreeChart chart = ChartFactory.createPieChart(
	      "Smart Phones Manufactured / Q3 2011",  // chart title
	      dataset,            // data
	      false,              // no legend
	      true,               // tooltips
	      false               // no URL generation
	  );

	  // set a custom background for the chart
	  chart.setBackgroundPaint(new GradientPaint(new Point(0, 0), 
		  new Color(20, 20, 20), new Point(400, 200), Color.DARK_GRAY));

	  // customise the title position and font
	  TextTitle t = chart.getTitle();
	  t.setHorizontalAlignment(HorizontalAlignment.LEFT);
	  t.setPaint(new Color(240, 240, 240));
	  t.setFont(new Font("Arial", Font.BOLD, 26));

	  PiePlot plot = (PiePlot) chart.getPlot();
	  plot.setBackgroundPaint(null);
	  plot.setInteriorGap(0.04);
	  plot.setOutlineVisible(false);

	  plot.setDefaultSectionOutlinePaint(Color.WHITE);
	  plot.setSectionOutlinesVisible(true);

	  // customise the section label appearance
	  plot.setLabelFont(new Font("Courier New", Font.BOLD, 20));
	  plot.setLabelLinkPaint(Color.WHITE);
	  plot.setLabelOutlineStroke(null);
	  plot.setLabelPaint(Color.WHITE);
	  plot.setLabelBackgroundPaint(null);
	  
	  // add a subtitle giving the data source
	  TextTitle source = new TextTitle("Source: http://www.bbc.co.uk/news/business-15489523", new Font("Courier New", Font.PLAIN, 12));
	  source.setPaint(Color.WHITE);
	  source.setPosition(RectangleEdge.BOTTOM);
	  source.setHorizontalAlignment(HorizontalAlignment.RIGHT);
	  chart.addSubtitle(source);
	  return chart;
  }

    private static PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Samsung", new Double(27.8));
        dataset.setValue("Others", new Double(55.3));
        dataset.setValue("Nokia", new Double(16.8));
        dataset.setValue("Apple", new Double(17.1));
        return dataset;
    }

    /**
     * Entry point.
     *
     * @param args  command line arguments (ignored).
     *
     * @throws IOException if there is an input/output problem.
     */
    public static void main(String[] args) throws IOException {
        PieDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset); 

        // we need to layout the legend to know how much space it requires
        // note that it is also possible to call arrange() with some
        // constraints on the layout
        BufferedImage img = new BufferedImage(1, 1,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.dispose();

        // now create an image of the required size for the legend
        int w = (int) Math.rint(800);
        int h = (int) Math.rint(600);
        BufferedImage img2 = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g22 = img2.createGraphics();
        chart.draw(g22, new Rectangle2D.Double(0, 0, w, h));
        g22.dispose();

        // ...and save it to a PNG image
        OutputStream out = new BufferedOutputStream(new FileOutputStream(
                new File("output2.png")));
        ChartUtils.writeBufferedImageAsPNG(out, img2);
        out.close();
        System.out.println("output2.png created"); 
    }
}
