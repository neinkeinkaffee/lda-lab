var title = d3.select("#title").text();
var document_url = "http://localhost:8080/api/corpus/" + corpusName + "/numberOfTopics/" + numberOfTopics + "/timestamp/" + timestamp + "/document/" + title;

d3.request(decodeURI(document_url))
    .mimeType("application/json")
    .response(function (xhr) {
        return JSON.parse(xhr.responseText);
    })
    .get(function (data) {
        var topicProbabilities = data.topicProbabilities.map(
            function(row) {
                var topic = row.topic;
                var topicProbability = {};
                topicProbability["topicId"] = topic.topicId;
                topicProbability["topWords"] = topic.topWords;
                topicProbability["probability"] = row.probability;
                return topicProbability
            }
        )
        util.buildTable(topicProbabilities, "documentTopics", "topic", "topicId", "topWords");
    });