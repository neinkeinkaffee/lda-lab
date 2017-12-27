var title = d3.select("#title").text();
var documentUrl = "http://localhost:8080/api/corpus/" + corpusName + "/numberOfTopics/" + numberOfTopics + "/timestamp/" + timestamp + "/document/" + title;

var colorRamp = d3.scaleLinear()
    .domain([0, numberOfTopics * .25, numberOfTopics * .5, numberOfTopics * .75, numberOfTopics])
    .range(["red", "blue", "green", "cyan", "magenta"]);

function markTopic(selector, topicId) {
    d3.selectAll(selector)
        .style("background-color", function () {
            if (this.id == topicId)
                return colorRamp(this.id);
            else
                return "white";
        })
        .style("color", function () {
            if (this.id == topicId)
                return "white";
            else
                return colorRamp(this.id);
        });
}

d3.request(decodeURI(documentUrl))
    .mimeType("application/json")
    .response(function (xhr) {
        return JSON.parse(xhr.responseText);
    })
    .get(function (data) {
        var topicProbabilities = data.topicProbabilities.map(
            function (row) {
                var topic = row.topic;
                var topicProbability = {};
                topicProbability["topicId"] = topic.topicId;
                topicProbability["topWords"] = topic.topWords;
                topicProbability["probability"] = row.probability;
                return topicProbability
            }
        )
        util.buildTable(topicProbabilities, "documentTopics", "topic", "topicId", "topWords");
        d3.selectAll(".topWords")
            .style("color", function () {
                var parentNode = d3.select(this).node().parentNode;
                var topicId = d3.select(parentNode).select("a").text();
                d3.select(this)
                    .attr("id", topicId)
                    .on("click", function () {
                        markTopic(".topWords", topicId);
                        markTopic(".contentToken", topicId);
                    });
                return colorRamp(topicId);
            });
        d3.selectAll(".contentToken")
            .style("color", function () {
                var topicId = this.id;
                d3.select(this)
                    .on("click", function () {
                        markTopic(".topicWords", topicId);
                        markTopic(".contentToken", topicId);
                    });
                return colorRamp(topicId);
            });
    });

d3.selectAll(".contentToken")
    .style("color", function () {
        return colorRamp(this.id)
    });