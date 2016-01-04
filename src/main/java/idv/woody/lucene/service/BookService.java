package idv.woody.lucene.service;

import idv.woody.lucene.dao.BookDao;
import idv.woody.lucene.model.Book;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;

/**
 * @author woody
 *
 */
//org.apache.lucene.analysis
//分析提供自帶的各種分析儀
//org.apache.lucene.collat​​ion
//包含collationKeyFilter和collationKeyAnalyzer两个相同功能的类，将所有token转为CollationKey，与IndexableBinaryStringTools一起存为term
//org.apache.lucene.document
//文件包中是文檔相關各種數據結構，如文檔類，外勤類等
//org.apache.lucene.index
//index包中是索引的读写操作类，常用的是对索引文件的segment进行写、合并和优化的IndexWriter类和对索引进行读取和删除操作的IndexReader类
//org.apache.lucene.queryParser
//QueryParser的包中是解析查詢語句相關的類（常用的是QueryParser的類）
//org.apache.lucene.search
//检索管理，根据查询条件，检索得到结果search包中是从索引中进行搜索的各种不同的Query类(如TermQuery、BooleanQuery等)和搜索结果集Hits类
//org.apache.lucene.store
//
//store包中是索引的存储相关类，如Directory类定义了索引文件的存储结构，FSDirectory是存储在文件系统（即磁盘）中的索引存储类，RAMDirectory为存储在内存中的索引存储类
//org.apache.lucene.util
//UTIL包中是公共工具類，例如時間和字符串之間的轉換工具
public class BookService {
	private List<Book> books;
	private File luceneIndexDirectory;
	
	public BookService(List<Book> books, File luceneIndexDirectory) {
		this.books = books;
		this.luceneIndexDirectory = luceneIndexDirectory;
		indexBooks();
	}

	private void indexBooks(){
		System.out.println("index books");
		boolean create = true;
		Date start = new Date();
		IndexWriter writer = null;
		Directory dir = null;
		try {
			if (!luceneIndexDirectory.exists() && !luceneIndexDirectory.mkdirs()) {
	            throw new RuntimeException("Failed to create lucene index direactory " + luceneIndexDirectory.getAbsolutePath());
	        }
//			FSDirectory :硬碟路徑，在硬碟建立index file
//			NIOFSDirectory: 硬碟路徑，以non-blocking IO方式在硬碟建立index file
//			RAMDirectory:在memory建立index file
			dir = new NIOFSDirectory(luceneIndexDirectory);
//			StandardAnalyzer	A sophisticated general-purpose analyzer.
//			WhitespaceAnalyzer	A very simple analyzer that just separates tokens using white space.
//			StopAnalyzer	    Removes common English words that are not usually useful for indexing.
//			SnowballAnalyzer	An interesting experimental analyzer that works on word roots (a search on rain should also return entries with raining, rained, and so on).
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36, analyzer);
			if(create){
				// Create a new index in the directory, removing any
				// previously indexed documents:
				iwc.setOpenMode(OpenMode.CREATE);
			}else{
				// Add new documents to an existing index:
				iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}
			// Optional: for better indexing performance, if you
			// are indexing many documents, increase the RAM
			// buffer.  But if you do this, increase the max heap
			// size to the JVM (eg add -Xmx512m or -Xmx1g):
			//
			// iwc.setRAMBufferSizeMB(256.0);
			writer = new IndexWriter(dir, iwc);
			writer.deleteAll();
			System.out.println("index for the book: " + books.get(0).getAuthor());
			for (Book book : books) {
				writer.addDocument(addDoc(writer, book));
			}
			
			writer.commit();
			// NOTE: if you want to maximize search performance,
			// you can optionally call forceMerge here.  This can be
			// a terribly costly operation, so generally it's only
			// worth it when your index is relatively static (ie
			// you're done adding documents to it):
			//
			// writer.forceMerge(1);
			
			Date end = new Date();
			System.out.println(end.getTime() - start.getTime() + " total milliseconds");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
                writer.close();
                dir.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
	}
	
	/*
	 * Pre-4.0.0 we use document.add(new Field("id", object.getId(), Store.YES, Index.NOT_ANALYZED));
	 * Store.YES – Value is stored in index, so it can be retrieved by an IndexReader.
	 * Store.NO – Not stored :)
	 * Index.No – not indexed, so not searchable (… and in my case, if you don’t index the “ID” field, than it can’t be searchable, which means it can’t be deleted and/or updated)
	 * Index.ANALYZED – Field will be indexed, and it will be analyzed (saved as tokens that will be searchable)
	 * Index.NOT_ANALYZED – Field will be indexed but in it’s original form (good for things that should be searchable in original form, ID anybody? :))
	 */
	// NORM權重 will affect the order
//		http://m.zgxue.com/166/1668070.html		
//		Index.ANALYZED: Index the tokens produced by running the field's value through an Analyzer.
//		Index.ANALYZED_NO_NORMS: Expert: Index the tokens produced by running the field's value through an Analyzer, and also separately disable the storing of norms.
//		Index.NO: Do not index the field value.
//		Index.NOT_ANALYZED: Index the field's value without using an Analyzer, so it can be searched. 
//		(means that the text is not broken down into individual tokens. In this case it can only match exact queries)
//		Index.NOT_ANALYZED_NO_NORMS: Expert: Index the field's value without an Analyzer, and also disable the storing of norms.

	private Document addDoc(IndexWriter writer, Book book) {
		System.out.println("creating document for the book: " + book);
		Document document = new Document();
		
		
		// search for exactly match
		// NOT_ANALYZED means that the text is not broken down into individual tokens. In this case it can only match exact queries
		document.add(new Field("author", book.getAuthor(), Store.YES, Index.ANALYZED));
		document.add(new Field("title", book.getTitle(), Store.YES, Index.ANALYZED_NO_NORMS));
		boolean analyzeNoNorms = true;
		// NumericField has the ability to perform a query on a range of values
		NumericField priceField = new NumericField("price", Store.NO, analyzeNoNorms);
		priceField.setDoubleValue(book.getPrice());
		document.add(priceField);
		document.add(new Field("publishDate", 
				DateTools.dateToString(book.getPublishDate(), DateTools.Resolution.MINUTE), Store.YES, Index.ANALYZED_NO_NORMS));
		document.add(new Field("isbn", book.getIsbn(), Store.YES, Index.NOT_ANALYZED_NO_NORMS));
		return document;
	}
	
	public List<Book> searchBooks(final String title, final String author, final String isbn, final Date publishDate, final double price) throws IOException {
		IndexSearcher searcher = null;
		Directory directory = null;
		IndexReader reader = null;
		
		boolean doInit = false;
		try {
			directory = new NIOFSDirectory(luceneIndexDirectory);
			doInit = !IndexReader.indexExists(directory);
		} catch (Exception e) {
			doInit = true;
		}
		
		if(doInit){
			indexBooks();
		}
		
		try {
			reader = IndexReader.open(directory);
			searcher = new IndexSearcher(reader);
			Query query = buildQuery(title, author, isbn, publishDate, price);
			int limit = 10;
			ScoreDoc[] docs = searcher.search(query, limit).scoreDocs;
			System.out.println("result size: " + docs.length);
			for (ScoreDoc scoreDoc : docs) {
				System.out.println();
				Document doc = searcher.doc(scoreDoc.doc);
				System.out.println(String.format("score: %s, title: %s", scoreDoc.score, doc.get("title")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			searcher.close();
			reader.close();
			directory.close();
		}
		
		return null;
	}
	
	// TermQuery: A Query that matches documents containing a term.
	// BooleanQuery: A Query that matches documents matching boolean combinations of other queries, e.g. TermQuerys, PhraseQuerys or other BooleanQuerys.
	// PhraseQuery:  Query that matches documents containing a particular sequence of terms. A PhraseQuery is built by QueryParser for input like "new york".
	// WildcardQuery: Supported wildcards are *, which matches any character 
	// sequence (including the empty one), and ?, which matches any single character. '\' is the escape character.
	// Note this query can be slow, as it needs to iterate over many terms. In order to prevent extremely slow 
	// WildcardQueries, a Wildcard term should not start with the wildcard * 
	private Query buildQuery(final String title, final String author, final String isbn, final Date publishDate, final double price) throws ParseException {
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
		final BooleanQuery booleanQuery = new BooleanQuery();
		final String wildcardPattern = "*%s*";
		// with substring to test if the wildcard query works as expected
//		When you use QueryParser, it uses analyzer which does the same sequence of actions as when during the indexing 
//		(tokenization, lowercasing, stopwords, etc.).
//		StandardAnalyzer transforms to lowercase		
//		When you use raw TermQuery, you need to do all these steps yourself.
		QueryParser titleParser = new QueryParser(Version.LUCENE_36, "title", analyzer);
		titleParser.setAllowLeadingWildcard(true);
		booleanQuery.add(titleParser.parse(String.format(wildcardPattern, StringUtils.substring(title, 3, 8))), Occur.MUST);
		QueryParser authorParser = new QueryParser(Version.LUCENE_36, "author", analyzer);
		booleanQuery.add(authorParser.parse(author), Occur.MUST);
		booleanQuery.add(new TermQuery(new Term("isbn", isbn)), Occur.MUST);
		booleanQuery.add(new TermQuery(new Term("publishDate", DateTools.dateToString(publishDate, DateTools.Resolution.MINUTE))), Occur.MUST);
		boolean minInclusive = true;
		boolean maxInclusive = true;
		NumericRangeQuery<Double> priceQuery = NumericRangeQuery.newDoubleRange("price", price - 1d, price + 1d, minInclusive, maxInclusive);
		booleanQuery.add(priceQuery, Occur.MUST);
		System.out.println("query: "+ booleanQuery);

		return booleanQuery;
	}
}
