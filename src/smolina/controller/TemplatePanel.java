package smolina.controller;

import java.awt.BorderLayout;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class TemplatePanel extends JPanel {
	private JTree tree;

	public TemplatePanel() {
		setBorder(BorderFactory.createEtchedBorder());
		setBorder(LineBorder.createGrayLineBorder());
		JScrollPane scrollp = new JScrollPane(createTree());
		this.setLayout(new BorderLayout());
		this.add(scrollp);
	}

	JTree createTree() {
		DefaultMutableTreeNode root;

		root = readFiles("jslife-20121230//jslife");
		tree = new JTree(root);
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent();
				if (node == null)
					return;
				if (!node.isLeaf())
					return;

				String regex = ".lif";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(node.toString());
				if (matcher.find()) {

					TreeNode[] nodes = node.getPath();

					StringBuilder pathBuilder = new StringBuilder();
					for (TreeNode nd : nodes) {
						pathBuilder.append(nd.toString());
						pathBuilder.append(File.separator);

					}
					String path = pathBuilder.toString();
					path = path.substring(0, path.length() - 1);
					System.out.println(path);
				}
			}
		});
		return tree;
	}

	DefaultMutableTreeNode readFiles(String pathDir) {
		File directory = new File(pathDir);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(
				directory.getName());
		DefaultMutableTreeNode subNode;
		File[] files = directory.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				subNode = readFiles(f.getPath());
				root.add(subNode);
			} else {
				subNode = new DefaultMutableTreeNode(f.getName());
				root.add(subNode);
			}
		}
		return root;
	}
}
