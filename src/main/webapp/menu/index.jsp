<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<% String name = (String) session.getAttribute("name"); %>
<t:layout pageTitle="Welcome ">

    <body>
    <!-- Navigation aside -->
    <aside id="navAside" class="bg-gray-800 text-white h-screen w-64 fixed left-0 top-0 flex flex-col justify-between">
        <div class="py-4 px-6">
            <h2 class="text-lg font-bold mb-4">Menu</h2>
            <ul>
                <li><a href="#">S’inscrire</a></li>
                <li><a href="#">Visualiser un cours</a></li>
                <li><a href="#">Rechercher des cours</a></li>
                <li><a href="#">Visualiser les notes des cours</a></li>
            </ul>
        </div>
        <div class="py-4 px-6">
            <!-- Display the name of the logged-in user -->

            <!-- Button to toggle navigation aside -->
            <button id="toggleNavOpen" class="mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">Open Navigation</button>
            <button id="toggleNavClose" class="hidden mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">Close Navigation</button>
        </div>
    </aside>

    <!-- Main content area -->
    <main id="mainContent" class="ml-64 p-8">
        <!-- Your main content here -->
    </main>

    <!-- JavaScript to toggle navigation aside -->
    <script>
        const navAside = document.getElementById('navAside');
        const toggleNavOpen = document.getElementById('toggleNavOpen');
        const toggleNavClose = document.getElementById('toggleNavClose');

        toggleNavOpen.addEventListener('click', function () {
            navAside.classList.remove('hidden');
            toggleNavOpen.classList.add('hidden');
            toggleNavClose.classList.remove('hidden');
        });

        toggleNavClose.addEventListener('click', function () {
            navAside.classList.add('hidden');
            toggleNavOpen.classList.remove('hidden');
            toggleNavClose.classList.add('hidden');
        });
    </script>
    </body>
</t:layout>
