class TextAreaPage extends Backbone.View
    initialize: ->
        $("#textId").keyup @updateStats
    updateStats: ->
        textArea = $("#textId")
        text = textArea.val()
        length = text.length
        col = 12
        visibleLines = Math.floor(length/col) + 1

        textArea.attr('rows', visibleLines)
        $("#rowId").text(visibleLines)
        $("#colId").text(col)
        $("#charsId").text(length)

# ------------------------------------- INIT APP
$ -> 
    drawer = new TextAreaPage 

#    Backbone.history.start
#       pushHistory: true
