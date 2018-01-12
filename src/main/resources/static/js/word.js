var lemma = d3.select("#lemma").text();
var lemma_url = util.baseUrl() + "/api/corpus/" + corpusName + "/numberOfTopics/" + numberOfTopics + "/timestamp/" + timestamp + "/word/" + lemma;

d3.request(decodeURI(lemma_url))
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
        util.buildTable(topicProbabilities, "wordTopics", "topic", "topicId", "topWords");
    });