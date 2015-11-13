<h1>User requirements</h1>
<h2>List of roles</h2>
<table border='1'>
<tr>
<th>ID</th>
<th>Name</th>
<th>Description</th>
</tr>
<tr>
<td>1000</td>
<td><a href='R1000.md'>Unauthorized user</a></td>
<td>can’t do anything but authorization</td>
</tr>
<tr>
<td>2000</td>
<td><em><a href='R2000.md'>Authorized user</a></em></td>
<td>any user which has own user name and password</td>
</tr>
<tr>
<td>2100</td>
<td>Administrator</td>
<td>controls lists of users and projects</td>
</tr>
<tr>
<td>2200</td>
<td><em>Employee</em></td>
<td>any user which works on some project</td>
</tr>
<tr>
<td>2210</td>
<td>Manager</td>
<td>forms teams, distributes tasks between teams leaders</td>
</tr>
<tr>
<td>2220</td>
<td>Team leader</td>
<td>team leader distributes tasks between developers of the team</td>
</tr>
<tr>
<td>2230</td>
<td>Developer</td>
<td>views the tasks and marks success of their execution</td>
</tr>
<tr>
<td>2231</td>
<td>Analyst</td>
<td>formulates requirements</td>
</tr>
</table>
<h2>Use case diagram: roles</h2>
<img src='https://projects-management.googlecode.com/svn/wiki/images/actors.png'>
<p>Each user of system can play several roles. Role of the user depends on project (except role «administrator»). For example, user can play role «team leader» in one project, and in the same time this user can play role «developer» in other project.</p>
<p><a href='index.md'>home</a></p>