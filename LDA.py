
import os, re, random
from nltk.corpus import stopwords as nltk_stopwords
from nltk.stem.porter import PorterStemmer
import math
from collections import defaultdict

class LDA(object):

	def __init__(self, topics=4, docs="data/docs", stopwords="data/stopwords.txt"):
		self.topics = topics

		# Get a full list of stopwords
		with open(stopwords, 'r') as stopwords:
			self.stopwords = stopwords.read().split()
			self.stopwords = set(self.stopwords).union(set(nltk_stopwords.words('english')))

		""" ------------------------------------------------ """

		# BUILD VOCABULARY SET: remove stop words and make vocabulary sets and document lists
		self.documents  = list()
		self.vocabulary = set()

		append = self.documents.append
		stem   = PorterStemmer().stem
		sub    = re.compile(r"\W+").sub

		for doc in os.listdir(docs):

			with open("{}/{}".format(docs, doc), 'r') as document:
				doc = (sub("", w) for w in document.read().lower().split() if w not in self.stopwords and len(w) < 20)
				doc = [stem(w) for w in doc]
				self.vocabulary = self.vocabulary.union(set(doc))

			if len(doc) > 0:
				append(doc)

		""" ------------------------------------------------ """

		# Replace words in docs with associated vocabulary index
		self.documents_num 	= list()
		self.vocabulary = list(self.vocabulary)
		append_num = self.documents_num.append

		# Loop through the documents in document set
		for doc in self.documents:
			doc_num = list()
			append_doc_num = doc_num.append

			# Convert each word into its corresponding index in the vocabulary
			for w in doc:

				for i, pv in enumerate(self.vocabulary):

					if pv==w:
						append_doc_num(i)

			append_num(doc_num)

		""" ------------------------------------------------ """
		"""
		# ASSIGN VOCABULARY TO TOPICS
		self.topic_distribution = defaultdict(list)

		for doc in self.documents_num:

			for w in doc:
				random_topic_assignment = int(round(random.random()*topics))
				self.topic_distribution[random_topic_assignment].append(w)

		# CALCULATE TOPIC PROBABILITIES IN DOCUMENTS
		for topic_number, topic_list in self.topic_distribution.items():
			topic_set = set(topic_list)
			#print topic_number, len(topic_list), len(topic_set)
			for t in topic_list:
				print topic_number, self.tfidf(t)
		"""

		#print self.vocabulary
		print [len(d) for d in self.documents_num]
		print self.documents

		""" ------------------------------------------------ """

		self.documents_by_topic = list()
		append = self.documents_by_topic.append

		# Assign topics to words in each document
		for doc in self.documents_num:
			append(self.assign_topics(doc))

		print self.documents_by_topic

		for d in self.documents_by_topic:
			for t in set(d):
				print t, d.count(t)/(1.*len(d))


		#for document in self.documents_by_topic:
		#	for topic in xrange(topics):
		#		print "Topic: {}\tPercentage: {}".format(topic, document.count(topic)/(1.*len(document)))

		#for i in xrange(len(self.vocabulary)):
		#	print self.documents_num[0].count(i)/(1.*len(self.documents_num[0]))

	def assign_topics(self, doc):
		new_document = list()
		assign_topic = new_document.append

		for w in doc:
			topic = random.sample(xrange(self.topics), 1)[0]
			assign_topic(topic)

		return new_document

	def term_frequency(self, word):
		try:
			w = word
			if type(word)==type(str):
				w = self.vocabulary.index(word)
			return [doc.count(w)/(len(doc)*1.) for doc in self.documents_num]
		except(ValueError):
			return [0 for doc in self.documents_num]

	def document_frequency(self, word):
		try:
			w = word
			if type(word)==type(str):
				w = self.vocabulary.index(word)
			counts = [1 if w in doc else 0 for doc in self.documents_num]
			return math.log((1.*len(counts))/sum(counts))
		except(ValueError, ZeroDivisionError):
			return 0

	def tfidf(self, word):
		tf = self.term_frequency(word)
		df = self.document_frequency(word)
		#print word, df, tf
		return [t*df for t in tf]


if __name__ == '__main__':
	l = LDA()
	#print l.term_frequency("read"), l.term_frequency("eat"), l.tfidf("like")
	#print l.document_frequency("eat")
	#print l.tfidf("read"); print l.tfidf("eat"); print l.tfidf("like")
	#l.split_topics(); 
	#l.tfidf()
	#tf = l.term_frequency("mga"); idf = l.inverse_document_frequency("mga")
	#print [t/idf for t in tf]

	#print "SAMPLE", random.sample(xrange(3),1)
