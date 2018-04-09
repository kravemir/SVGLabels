import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.commons.io.IOUtils;
import org.kravemir.svg.labels.api.LabelGroup;
import org.kravemir.svg.labels.api.DocumentRenderOptions;
import org.kravemir.svg.labels.api.TileRenderer;
import org.kravemir.svg.labels.api.TiledPaper;
import org.kravemir.svg.labels.impl.TilePaperImpl;
import org.kravemir.svg.labels.impl.TileRendererImpl;
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
        TiledPaper paper1 = new TilePaperImpl(297,210,5,5,68,46.25,5,5);
        TiledPaper paper2 = new TilePaperImpl(297,210,5,5,85,46.25,5,5);
        SVGDocument svg1 = loadTemplate("/label01.svg");
        SVGDocument svg2 = loadTemplate("/label02.svg");
        TileRenderer tileRenderer = new TileRendererImpl();

        String test = "multiple templates, multiple pages";
        ArrayList<LabelGroup> labels = new ArrayList<>();
        labels.add(new LabelGroup(svg1, 14));
        labels.add(new LabelGroup(svg2, 8));
        DocumentRenderOptions options =  DocumentRenderOptions.builder()
                .withRenderPageBorders(true)
                .withRenderTileBorders(true)
                .withRenderLabelBorders(true)
                .build();
        tests.addItem( new Wrapper<>( tileRenderer.render(paper2,labels, options), test) );

        test = "fill one page";
        labels.clear();
        labels.add(new LabelGroup(svg2, 7));
        labels.add(new LabelGroup(svg1, LabelGroup.FILL_PAGE));
        options = DocumentRenderOptions.builder()
                .withRenderPageBorders(true)
                .withRenderLabelBorders(true)
                .build();
        tests.addItem( new Wrapper<>( tileRenderer.render(paper2,labels, options), test) );

        test = "fill pages";
        labels.clear();
        labels.add(new LabelGroup(svg2, LabelGroup.FILL_PAGE));
        labels.add(new LabelGroup(svg1, LabelGroup.FILL_PAGE));
        options = DocumentRenderOptions.builder()
                .withRenderTileBorders(true)
                .build();
        tests.addItem( new Wrapper<>( tileRenderer.render(paper1,labels, options), test) );

        test = "one template, multiple pages";
        labels.clear();
        labels.add(new LabelGroup(svg1, 20));
        options = DocumentRenderOptions.builder()
                .withRenderPageBorders(true)
                .build();
        tests.addItem( new Wrapper<>( tileRenderer.render(paper2,labels, options), test) );

        test = "no template (only positions)";
        labels.clear();
        labels.add(new LabelGroup(null, LabelGroup.FILL_PAGE));
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
