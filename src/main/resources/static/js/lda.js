var colorRamp = d3.scaleLinear()
    .domain([0, numberOfTopics * .25, numberOfTopics * .5, numberOfTopics * .75, numberOfTopics])
    .range(["red", "blue", "green", "cyan", "magenta"]);

d3.selectAll(".expandVolumes")
    .on("click", function () {
        var section = this.id.split("_")[1];
        d3.select("#section_" + section)
            .style("display", "block");
        d3.select("#collapseVolumes_" + section)
            .style("display", "inline");
        d3.select(this)
            .style("display", "none");
    });

d3.selectAll(".collapseVolumes")
    .on("click", function () {
        var section = this.id.split("_")[1];
        d3.select("#section_" + section)
            .style("display", "none");
        d3.select("#expandVolumes_" + section)
            .style("display", "inline");
        d3.select(this)
            .style("display", "none");
    });

d3.selectAll(".expandDocuments")
    .on("click", function () {
        var volume = this.id.split("_")[1];
        d3.select("#volume_" + volume)
            .style("display", "block");
        d3.select("#collapseDocuments_" + volume)
            .style("display", "inline");
        d3.select(this)
            .style("display", "none");
    });

d3.selectAll(".collapseDocuments")
    .on("click", function () {
        var volume = this.id.split("_")[1];
        d3.select("#volume_" + volume)
            .style("display", "none");
        d3.select("#expandDocuments_" + volume)
            .style("display", "inline");
        d3.select(this)
            .style("display", "none");
    });

d3.selectAll(".expandTopics")
    .on("click", function () {
        var documentTitle = this.id.split("_").slice(1).join("_");
        var topicsTable = "#" + documentTitle + "Table";
        d3.select(this)
            .style("display", "none");
        d3.select("#collapseTopics_" + documentTitle)
            .style("display", "inline");
        var documentUrl = "http://localhost:8080/api/corpus/" + corpusName + "/numberOfTopics/" + numberOfTopics + "/timestamp/" + timestamp + "/document/" + documentTitle;
        if (d3.select(topicsTable).selectAll("tbody").empty()) {
            fetchDocumentDataAndBuildTopicsTable(documentUrl, documentTitle);
        }
        d3.select("#" + documentTitle + "Table")
            .style("display", "block");

    });

d3.selectAll(".collapseTopics")
    .on("click", function () {
        var documentTitle = this.id.split("_").slice(1).join("");
        var topicsTable = "#" + documentTitle + "Table";
        d3.select(this)
            .style("display", "none");
        d3.select("#expandTopics_" + documentTitle)
            .style("display", "inline");
        d3.select(topicsTable)
            .style("display", "none");
    });

function fetchDocumentDataAndBuildTopicsTable(documentUrl, documentTitle) {
    d3.request(decodeURI(documentUrl))
        .mimeType("application/json")
        .response(function (xhr) {
            console.log(xhr.responseText);
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
            util.buildTable(topicProbabilities, documentTitle, "topic", "topicId", "topWords");
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
        });
}