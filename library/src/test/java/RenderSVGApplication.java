import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.commons.io.IOUtils;
import org.kravemir.svg.labels.*;
import org.w3c.dom.svg.SVGDocument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RenderSVGApplication {


    public static void main(String[] args) {
        new RenderSVGApplication().run();
    }

    static class Wrapper<T>{
        private T object;
        private String text;

        public Wrapper(T object, String text) {
            this.object = object;
            this.text = text;
        }

        public T getObject() {
            return object;
        }

        @Override
        public String toString() {
            return text;
        }

    }

    private void run() {
        JFrame f = new JFrame("SVG TilerF");
        f.setSize(1280,800);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));
        f.setVisible(true);

        JSVGCanvas c = new JSVGCanvas();
        f.getContentPane().add(c);

        final JComboBox<Wrapper<List<SVGDocument>>> tests = new JComboBox<>();
        final JComboBox<Wrapper<SVGDocument>> pages = new JComboBox<>();

        addTests(tests);

        tests.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Wrapper<?> selected = (Wrapper<?>)tests.getSelectedItem();

                pages.removeAllItems();

                if(selected == null) return;
                int i = 0;
                for(Object d : (List<?>) selected.getObject())
                    pages.addItem(new Wrapper<>((SVGDocument)d, Integer.toString(++i)));

                pages.setSelectedIndex(0);
            }
        });

        pages.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Wrapper<?> selected = (Wrapper<?>)pages.getSelectedItem();
                if(selected == null) return;
                c.setSVGDocument( (SVGDocument) selected.getObject() );
            }
        });

        tests.setSelectedIndex(0);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        bottomPanel.add(new Label("Test"));
        bottomPanel.add(tests);

        bottomPanel.add(new Label("Page"));
        bottomPanel.add(pages);

        bottomPanel.setMaximumSize(new Dimension(100000, 32));

        tests.setPreferredSize(new Dimension(400,32));
        pages.setPreferredSize(new Dimension(400,32));

        tests.setMaximumSize(new Dimension(400,32));
        pages.setMaximumSize(new Dimension(400,32));

        f.getContentPane().add(bottomPanel, BorderLayout.CENTER);

        f.setVisible(true);
    }

    private void addTests(JComboBox<Wrapper<List<SVGDocument>>> tests){
        TiledPaper paper1 = TiledPaper.builder()
                .withPaperSize(297,210)
                .withLabelOffset(5,5)
                .withLabelSize(85,46)
                .withLabelDelta(5,5)
                .build();
        TiledPaper paper2 = TiledPaper.builder()
                .withPaperSize(297,210)
                .withLabelOffset(5,5)
                .withLabelSize(85,46.25)
                .withLabelDelta(5,5)
                .build();
        SVGDocument svg1 = loadTemplate("/label01.svg");
        SVGDocument svg2 = loadTemplate("/label02.svg");
        TileRenderer tileRenderer = new TileRendererImpl();

        String test = "multiple templates, multiple pages";
        ArrayList<LabelGroup> labels = new ArrayList<>();
        labels.add(LabelGroup.builder().withTemplate(svg1).withCount(14).build());
        labels.add(LabelGroup.builder().withTemplate(svg2).withCount(8).build());
        DocumentRenderOptions options =  DocumentRenderOptions.builder()
                .withRenderPageBorders(true)
                .withRenderTileBorders(true)
                .withRenderLabelBorders(true)
                .build();
        tests.addItem( new Wrapper<>( tileRenderer.render(paper2,labels, options), test) );

        test = "fill one page";
        labels.clear();
        labels.add(LabelGroup.builder().withTemplate(svg2).withCount(7).build());
        labels.add(LabelGroup.builder().withTemplate(svg1).fillPage().build());
        options = DocumentRenderOptions.builder()
                .withRenderPageBorders(true)
                .withRenderLabelBorders(true)
                .build();
        tests.addItem( new Wrapper<>( tileRenderer.render(paper2,labels, options), test) );

        test = "fill pages";
        labels.clear();
        labels.add(LabelGroup.builder().withTemplate(svg2).fillPage().build());
        labels.add(LabelGroup.builder().withTemplate(svg1).fillPage().build());
        options = DocumentRenderOptions.builder()
                .withRenderTileBorders(true)
                .build();
        tests.addItem( new Wrapper<>( tileRenderer.render(paper1,labels, options), test) );

        test = "one template, multiple pages";
        labels.clear();
        labels.add(LabelGroup.builder().withTemplate(svg1).withCount(20).build());
        options = DocumentRenderOptions.builder()
                .withRenderPageBorders(true)
                .build();
        tests.addItem( new Wrapper<>( tileRenderer.render(paper2,labels, options), test) );

        test = "no template (only positions)";
        labels.clear();
        labels.add(LabelGroup.builder().fillPage().build());
        options = DocumentRenderOptions.builder()
                .withRenderPageBorders(true)
                .withRenderTileBorders(true)
                .build();
        tests.addItem( new Wrapper<>( tileRenderer.render(paper1,labels, options), test) );
    }

    private SVGDocument loadTemplate(String file) {
        try {
            String str = IOUtils.toString(this.getClass().getResourceAsStream(file));
            String parser = XMLResourceDescriptor.getXMLParserClassName();
            SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
            return factory.createSVGDocument("", new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
