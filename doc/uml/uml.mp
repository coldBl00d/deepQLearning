% Filename: uml.mp
input metauml;
beginfig(1);
    Interface.environment("Environment")
    ("+restart()",
    "+act()",
    "+getScreen()",
    "+Lost()",
    "+getAction()");
    drawObjects(environment);

    Class.aleEnvironment("ALE Environment")("+ale")
    ("+restart()",
    "+act()",
    "+getScreen()",
    "+Lost()",
    "+getAction()");
    topToBottom(50)(environment, aleEnvironment);
    drawObjects(aleEnvironment);
    clink(inheritance)(aleEnvironment, environment);

    Class.agent("Agent")(
    "+env",
    "+plotter",
    "+mem",
    "+buf",
    "+dqn",
    "+action",
    "+explorerate",
    "+train"
    )(
    "+step()",
    "+playrand()",
    "+train()",
    "+test()",
    "+play()"
    );

    leftToRight(50)(environment, agent);


    Class.plotter("Plotter")("values")
    ("+updateValues()");
    leftToRight(50)(agent, plotter);

    drawObjects(agent);
    drawObjects(plotter);

    clink(associationUni)(agent, plotter);
    clink(associationUni)(agent, environment);
endfig;
end
