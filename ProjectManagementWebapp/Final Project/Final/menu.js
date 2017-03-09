function loadprofile()
{
	$("#middle").load("myprofile.html");
}

function loadAddProject()
{
	$("#middle").load("createproject.html");
}
function viewProject()
{
	$("#middle").load("viewproject.html");
}

function addEmployee()
{
	$("#middle").load("addemployee.html");
}
function viewEmployee()
{
	$("#middle").load("viewemployee.html");
}

function addBudget()
{
	$("#middle").load("addbudget.html")
}
function viewBudget()
{
	$("#middle").load("viewbudget.html")
}
function addTask()
{
	$("#middle").load("addtask.html");
}
function viewTask()
{
	$("#middle").load("viewtask.html");
}
function addClient()
{
	$("#middle").load("addclient.html");
}
function viewClient()
{
	$("#middle").load("viewclient.html");
}
function calender()
{
	$("#middle").load("calender.html");
}
function report()
{
	$("#middle").load("pie-chart-with-index-label.html");
}

$('.form').find('input, textarea').on('keyup blur focus', function (e) {
  
  var $this = $(this),
      label = $this.prev('label');

	  if (e.type === 'keyup') {
			if ($this.val() === '') {
          label.removeClass('active highlight');
        } else {
          label.addClass('active highlight');
        }
    } else if (e.type === 'blur') {
    	if( $this.val() === '' ) {
    		label.removeClass('active highlight'); 
			} else {
		    label.removeClass('highlight');   
			}   
    } else if (e.type === 'focus') {
      
      if( $this.val() === '' ) {
    		label.removeClass('highlight'); 
			} 
      else if( $this.val() !== '' ) {
		    label.addClass('highlight');
			}
    }

});

$('.tab a').on('click', function (e) {
  
  e.preventDefault();
  
  $(this).parent().addClass('active');
  $(this).parent().siblings().removeClass('active');
  
  target = $(this).attr('href');

  $('.tab-content > div').not(target).hide();
  
  $(target).fadeIn(600);
  
});