function moveSelect(select1,select2,isAll) // move from select 2 to select 1
{
    select2Options = select2.options;
    for (i = 0 ; i < select2Options.length;i++){
        if ((select2Options[i].selected)||(isAll))
        {
            select1.options[select1.length]=
                new Option(
                    select2[i].text,select2[i].value
                );
            select2[i] = null;
            i--; // as select2.length change , adjust i for correct indexing
            if (!isAll)
                break;
        }
    }
}

function moveSelectWithCount(select1,select2,count) // move from select 2 to select 1
{
    select2Options = select2.options;
    for (i = 0 ; i < select2Options.length;i++){
        if ((select2Options[i].selected)||(count > 0 ))
        {
            select1.options[select1.length]=
                new Option(
                    select2[i].text,select2[i].value
                );
            select2[i] = null;
            i--; // as select2.length change , adjust i for correct indexing
            count --;
        }
    }
}


function processAssign(cmd,f,namePrefix){_processAssign(cmd,f,namePrefix,-1);}
function processAssign(cmd,f,namePrefix,maxAssigned){_processAssign(cmd,f,namePrefix,maxAssigned);}

function _processAssign(cmd,f,namePrefix,maxAssigned)
{
    assignedSelect = f.elements[namePrefix+'_ASSIGN'];
    unassignSelect = f.elements[namePrefix+'_UNASSIGN'];
    
    if ((cmd=="add"||cmd=="addAll")&&(maxAssigned>0)&&(assignedSelect.length == maxAssigned)){
        alert("Max Reached");
        return;
    }

    if (cmd=="add"){
        if (unassignSelect.selectedIndex!=-1)
            moveSelect(assignedSelect,unassignSelect,false);
    }else if (cmd=="addAll"){
        if (maxAssigned>0){
            availSpace = maxAssigned - assignedSelect.options.length;
            moveSelectWithCount(assignedSelect,unassignSelect,availSpace);
        }else{
            moveSelect(assignedSelect,unassignSelect,true);
        }
    }else if (cmd=="del"){
        if (assignedSelect.selectedIndex!=-1)
            moveSelect(unassignSelect,assignedSelect,false);
    }else if (cmd=="delAll"){
        moveSelect(unassignSelect,assignedSelect,true);
    }
}

function selectAllAssigned(f,namePrefix)
{
    for (var i = 0 ; i < f.elements[namePrefix+'_ASSIGN'].options.length; i++)
    {
        f.elements[namePrefix+'_ASSIGN'].options[i].selected= true;
    }
}

function isNoneAssigned(f,namePrefix){
    return (f.elements[namePrefix+'_ASSIGN'].options.length==0);
}

function validateForm(f,namePrefix,elename){
    selectAllAssigned(f,namePrefix);
    return true;
}