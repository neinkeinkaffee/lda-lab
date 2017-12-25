var topicId = d3.select("#topicId").text();
var topic_url = "http://localhost:8080/api/corpus/" + corpusName + "/numberOfTopics/" + numberOfTopics + "/timestamp/" + timestamp + "/topic/" + topicId;

d3.request(topic_url)
    .mimeType("application/json")
    .response(function (xhr) {
        return JSON.parse(xhr.responseText);
    })
    .get(function (data) {
        console.log(data);
        util.buildTable(data.wordProbabilities, "topicWords", "word", "lemma", null);
        util.buildTable(data.multiWordProbabilities, "topicMultiWords", null, null, "lemma");
        util.buildTable(data.documentProbabilities, "topicDocuments", "document", "title", null);
    });