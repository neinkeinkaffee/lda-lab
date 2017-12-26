var topicId = d3.select("#topicId").text();
var topic_url = "http://localhost:8080/api/corpus/" + corpusName + "/numberOfTopics/" + numberOfTopics + "/timestamp/" + timestamp + "/topic/" + topicId;

d3.request(topic_url)
    .mimeType("application/json")
    .response(function (xhr) {
        return JSON.parse(xhr.responseText);
    })
    .get(function (data) {
        var documentProbabilities = data.documentProbabilities.map(
            function(row) {
                var document = row.document;
                var documentProbability = {};
                documentProbability["title"] = document.title;
                documentProbability["meta"] = document.author + " (" + document.volume + ")";
                documentProbability["probability"] = row.probability;
                return documentProbability;
            }
        )
        util.buildTable(data.wordProbabilities, "topicWords", "word", "lemma", null);
        util.buildTable(data.multiWordProbabilities, "topicMultiWords", null, null, "lemma");
        util.buildTable(documentProbabilities, "topicDocuments", "document", "title", "meta");
    });